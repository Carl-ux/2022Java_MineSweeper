package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MineArea extends JPanel implements ActionListener, MouseListener{
    public JButton reStart;
    public Block[][] block;
    public BlockView[][] blockView;
    public LayMines lay;
    public int row, col;                        //雷区的行数、列数
    public double MineRate;                     //地雷密度
    public ImageIcon mark;                      //旗帜标记

    public JPanel pGame, pShow;
    public JTextField Player,showTime;          //显示玩家名称和用时
    public Timer time;                          //计时器
    public int spendTime = 0;

    String user_name;

    public JDialog lose = new JDialog();
    public JPanel panel;
    public JLabel str;
    public JButton restart1 = new JButton("重新开始");
    public JButton	cancel = new JButton("取消");

    public MineArea(int row,int col,double MineRate,String user_name)
    {
        this.user_name = user_name;
        reStart = new JButton("重新开始");
        mark = new ImageIcon("img/flag.png");     //flag标记
        time = new Timer(1000, this);

        Player = new JTextField(5);
        showTime = new JTextField(5);

        Player.setHorizontalAlignment(JTextField.CENTER);
        //Player.setFont(new Font("Arial", Font.BOLD, 16));
        Player.setDisabledTextColor(java.awt.Color.black);
        Player.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        Player.setEnabled(false);

        showTime.setHorizontalAlignment(JTextField.CENTER);
        showTime.setFont(new Font("Arial", Font.BOLD, 16));
        showTime.setDisabledTextColor(java.awt.Color.black);                                  //设置字体颜色
        showTime.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));  //边框颜色
        showTime.setEnabled(false);                               //不能响应用户输入

        pGame = new JPanel();
        pShow = new JPanel();

        lay = new LayMines();

        initMineArea(row,col,MineRate);
        reStart.addActionListener(this);

        Player.setText("玩家:" + user_name);
        pShow.add(Player);
        pShow.add(reStart);
        pShow.add(showTime);            //显示区域

        this.setLayout(new BorderLayout());
        add(pShow,BorderLayout.NORTH);
        add(pGame,BorderLayout.CENTER);
    }

    public void initMineArea(int row, int col,double MineRate)
    {
        pGame.removeAll();
        spendTime = 0;
        this.row = row;
        this.col = col;
        this.MineRate = MineRate;

        block = new Block[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                block[i][j] = new Block();
        }
        blockView = new BlockView[row][col];

        pGame.setLayout(new GridLayout(row,col));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                blockView[i][j] = new BlockView();
                //blockView[i][j].giveView(block[i][j]); // 给block[i][j]提供视图
                pGame.add(blockView[i][j]);
                blockView[i][j].getBlockCover().addActionListener(this);
                blockView[i][j].getBlockCover().addMouseListener(this);
                blockView[i][j].seeBlockCover();
                blockView[i][j].getBlockCover().setEnabled(true);
                blockView[i][j].getBlockCover().setIcon(null);
            }
        }
        validate();
    }

    public void initMine(int m,int n) {
        lay.layMinesForBlock(block, MineRate, m, n);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                blockView[i][j].giveView(block[i][j]);
            }
        }
    }


    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setMineRate(int mineRate) {
        this.MineRate = mineRate;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart1) {
            initMineArea(row, col, MineRate);
            lose.dispose();
            return;
        }
        if (e.getSource() == cancel) {
            lose.dispose();
        }

        int tmp = 0;
        if (e.getSource() != reStart && e.getSource() != time) {
            time.start();
            int m = -1, n = -1;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (block[i][j].getIsMine())
                        tmp = -1;
                    if (e.getSource() == blockView[i][j].getBlockCover()) {
                        m = i;
                        n = j;
                    }
                }
            }

            if (tmp == 0)
                initMine(m, n);                        //第一次不踩雷  初始化雷区

            if (block[m][n].getIsMine()) {              //踩雷
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        blockView[i][j].getBlockCover().setEnabled(false);
                        if (block[i][j].getIsMine())
                            blockView[i][j].seeBlockNameOrIcon();          //全部显示
                    }
                }
                panel = new JPanel();
                panel.setLayout(new FlowLayout());
                str = new JLabel("你输了", JLabel.CENTER);
                //restart1 = new JButton("重新开始");
                restart1.addActionListener(this);
                //cancel = new JButton("取消");
                cancel.addActionListener(this);
                lose.setTitle("失败");
                lose.setBounds(300, 100, 200, 150);
                lose.setResizable(false);
                lose.setVisible(false);
                lose.setModal(true);
                time.stop();
                spendTime = 0;

                lose.add(str, BorderLayout.CENTER);
                panel.add(restart1);
                panel.add(cancel);
                lose.add(panel, BorderLayout.SOUTH);
                lose.setLocationRelativeTo(null);            //Center the window
                lose.setVisible(true);
            }
            else
                show(m, n);        //翻开旁边
        }
        if(e.getSource() == reStart)
            initMineArea(row,col,MineRate);
        if(e.getSource() == time)
        {
            spendTime ++;
            showTime.setText("" + spendTime);
        }
        GameWin();

    }
    public void show(int m, int n) {
        if (block[m][n].getAroundMineNumber() > 0 && !block[m][n].getIsOpen()) {
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            return;
        } else if (block[m][n].getAroundMineNumber() == 0 && !block[m][n].getIsOpen()) {
            blockView[m][n].seeBlockNameOrIcon();
            block[m][n].setIsOpen(true);
            for (int k = Math.max(m - 1, 0); k <= Math.min(m + 1, row - 1); k++) {
                for (int t = Math.max(n - 1, 0); t <= Math.min(n + 1, col - 1); t++)
                    show(k, t);
            }
        }
    }

    public void GameWin()
    {

    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JButton source = (JButton) e.getSource();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (e.getModifiers() == InputEvent.BUTTON3_MASK && source == blockView[i][j].getBlockCover()) {
                    if (block[i][j].getIsMark()) {
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                    } else {
                        source.setIcon(mark);
                        block[i][j].setIsMark(true);

                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
