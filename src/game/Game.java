package game;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class Game extends JFrame implements ActionListener  {
    JMenuBar bar;
    JMenu Menu1,Menu2;
    JMenuItem defi;
    JMenuItem Developer;

    MineArea mineArea = null;
//自定义
    JDialog set = null;
    JPanel panel, panel1, panel2, panel3, panel4;
    JLabel label, label1, label2, label3;
    JTextField row = null, col = null, minerate = null;
    JButton OK,Cancel;

    public Game(JFrame mainFrame,String name)
    {
        mineArea = new MineArea(20,20,0.15,name);
        this.add(mineArea,BorderLayout.CENTER);
        bar = new JMenuBar();
        Menu1 = new JMenu("游戏");
        Menu2 = new JMenu("帮助");

        defi = new JMenuItem("自定义");
        Menu1.add(defi);

        Developer = new JMenuItem("开发者");
        Menu2.add(Developer);

        bar.add(Menu1);
        bar.add(Menu2);

        this.setJMenuBar(bar);

        defi.addActionListener(this);
        Developer.addActionListener(this);



        setBounds(300, 100, 480, 560); // 移动组件并调整大小
        this.setLocationRelativeTo(null);            //Center the window
        this.setVisible(true); // 使Window可见
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });                          //return to mainFrame when close this window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == defi)
        {
            set = new JDialog();
            set.setTitle("自定义难度");
            set.setBounds(300,100,300,130);
            set.setResizable(false);          //设置大小不可变
            set.setModal(true);               //设置为对话框模式

            panel = new JPanel();
            panel1 = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();
            panel4 = new JPanel();

            label = new JLabel("请输入行列数与密度：", JLabel.CENTER);
            label1 = new JLabel("行：", JLabel.CENTER);
            label2 = new JLabel("列：", JLabel.CENTER);
            label3 = new JLabel("地雷密度：", JLabel.CENTER);

            row = new JTextField();
            row.setText("20");
            row.setSize(1, 6);

            col = new JTextField();
            col.setText("20");

            minerate = new JTextField();
            minerate.setText("0.15");
            OK = new JButton("确认");
            OK.addActionListener(this);
            Cancel = new JButton("取消");
            Cancel.addActionListener(this);

            panel1.add(label1);
            panel1.add(row);

            panel2.add(label2);
            panel2.add(col);

            panel3.add(label3);
            panel3.add(minerate);

            panel4.add(OK);
            panel4.add(Cancel);

            panel.add(panel1);
            panel.add(panel2);
            panel.add(panel3);
            set.add(label, BorderLayout.NORTH);
            set.add(panel, BorderLayout.CENTER);
            set.add(panel4, BorderLayout.SOUTH);
            set.setLocationRelativeTo(null);            //Center the window
            set.setVisible(true);
        }

        if(e.getSource() == OK)
        {
            int rowN = Integer.parseInt(row.getText());
            int colN = Integer.parseInt(col.getText());
            double rate = Double.parseDouble(minerate.getText());

            mineArea.initMineArea(rowN,colN,rate);
            setBounds(100, 100, colN*30, rowN*30 + 80);
            set.setVisible(false);
        }
        if(e.getSource() == Cancel)
            set.setVisible(false);
    }
}
