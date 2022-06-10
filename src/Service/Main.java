package Service;/*
File:Service.Main.java
Author: Carl
Date:2022-05-27
Description:各功能模块统一调度  程序启动界面
LastEditor:
*/
import java.awt.event.*;
import javax.swing.*;
import game.*;

public class Main {
    private static int id = 0;    //User ID-login   0-not login yet
    private static String name;
    private static JLabel label_account;
    private static JLabel label_name;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("扫雷");
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);          //empty layout空布局

        label_account=new JLabel("账号:未登录");
        label_account.setBounds(5,5,400,30);
        frame.add(label_account);

        label_name=new JLabel("用户名:无");
        label_name.setBounds(5,20,400,30);
        frame.add(label_name);

        ImageIcon logo1=new ImageIcon("img/logo.png");
        JLabel logo=new JLabel();
        logo.setIcon(logo1);
        logo.setBounds(132,40,300,70);
        frame.add(logo);


        JButton btn_game=new JButton("开始游戏");

        btn_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id == 0)
                {
                    if(JOptionPane.showConfirmDialog(frame, "是否打开登录界面？", "未登录",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                        frame.setVisible(false);
                        new Account(frame);
                    }
                }
                else {
                    frame.setVisible(false);
                    new Game(frame,name);
                }
            }
        });
        btn_game.setBounds(frame.getWidth()/2-75,130,150,30);
        frame.add(btn_game);


        JButton btn_account=new JButton("账号管理");
        btn_account.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Account(frame);
            }
        });
        btn_account.setBounds(frame.getWidth()/2-75,170,150,30);
        frame.add(btn_account);


        JButton btn_charts=new JButton("排行榜");
        //TODO Chart Part
        btn_charts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Charts(frame);
            }
        });
        btn_charts.setBounds(frame.getWidth()/2-75,210,150,30);
        frame.add(btn_charts);



        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //由Account对象调用，登录成功则给id赋值
    public static void login(int ID,String Name) {
        id = ID;
        name = Name;
        label_account.setText("账号:" + id);
        label_name.setText("用户名:" + name);
    }
}
