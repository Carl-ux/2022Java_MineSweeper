package Service;/*
File:Service.Charts.java
Author: Carl
Date:2022-05-27
Description:对排行榜的设计
LastEditor:
*/
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Charts extends JFrame{
    private String DB_PATH = "data/rank.xml";
    Info[] List;             //store rank info

    public Charts(JFrame mainFrame) {
        this.setSize(500,350);
        this.setLocationRelativeTo(null);
        this.setTitle("排行榜");
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosed(WindowEvent e){
                mainFrame.setVisible(true);
            }
        });

        loadInfo();
        quickSort(List,0,List.length-1);

        String[] rank_list = new String[List.length + 1];
        rank_list[0] = String.format("%-12s%-20s%-20s%-25s%-20s", "排名","用户名","胜利用时","用户IP","完成时间");
        for(int i = 0;i < List.length;i++){
            rank_list[i + 1] = String.format("%-20s%-25s%-20s%-20s%-20s", i+1,List[i].name,List[i].Time_Game + "s",List[i].IP,List[i].Time_Record);
        }
        JList<String> time_charts = new JList<String>(rank_list);
        JScrollPane time_scroll = new JScrollPane(time_charts);
        time_scroll.setBounds(10,5,450,300);

        this.add(time_scroll);
        this.setVisible(true);
    }



    class Info {
        String name;
        String IP;
        int Time_Game;
        String Time_Record;

        public Info(String name,String IP,String Time_Game,String Time_Record) {
            this.name = name;
            this.IP = IP;
            this.Time_Game = Integer.parseInt(Time_Game);
            this.Time_Record = Time_Record;
        }
    }

    //TODO Load ranking info from rank.xml into List
    private void loadInfo() {
        Records records = new Records();
        List = new Info[records.getRecordCount()];
        for (int i = 0;i < List.length;i++){
            List[i] = new Info(records.getName(i),records.getIP(i),records.getTimeWin(i),records.getTimeRecord(i));
        }
    }

    //用快速排序对排行榜以游戏用时排升序
    private void quickSort(Info[] info,int start,int end)
    {
        //递归结束
        if(start >= end)
            return;

        //找到key坐标
        int pivotIndex = partition(info,start,end);

        quickSort(info,start,pivotIndex - 1);
        quickSort(info,pivotIndex + 1,start);
    }
    //快排分割的算法
    private int partition(Info[] info,int start,int end)
    {
        //key元素用以比较
        Info pivot = info[start];

        int left = start;
        int right = end;

        while(left < right)
        {
            //先从右往左扫描，寻找比关键元素小的，并填入坑中
            while (left < right && info[right].Time_Game >= pivot.Time_Game)
                right--;
            if(left < right)
                info[left++] = info[right];

            //从左往右
            while (left < right && info[left].Time_Game < pivot.Time_Game)
                left++;
            if(left < right)
                info[right--] = info[left];
        }

        //扫描完成 left = right
        info[left] = pivot;
        return left;
    }
}
