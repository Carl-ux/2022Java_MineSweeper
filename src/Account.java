/*
File:Account.java
Author: Carl
Date:2022-05-27
Description:用户进行账号登录、注册和管理
LastEditor:
*/

import java.awt.event.*;
import javax.swing.*;

public class Account extends JFrame {
     Users users = new Users();
    public JTabbedPane jtab;       //选项卡面板组件

    //Construct method,mainFrame为主窗体
    public Account(JFrame mainFrame) {
        this.setTitle("账号管理");
        this.setSize(520, 380);
        this.setLocationRelativeTo(null);            //Center the window
        this.setResizable(false);                    //Window's size unchangeable
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.setVisible(true);
            }
        });                          //return to mainFrame when close this window

        jtab = new JTabbedPane(JTabbedPane.TOP);    //display on the top

        //面板组件JPanel
        JPanel panel_login = new PanelLogin();
        JPanel panel_register = new PanelRegister();
        JPanel panel_reset = new PanelReset();
        jtab.add("登录", panel_login);
        jtab.add("注册", panel_register);
        jtab.add("修改账户信息", panel_reset);
        this.add(jtab);
        this.setVisible(true);
    }


        //TODO 登录 注册 修改面板的内部实现
        class PanelLogin extends JPanel {

            public PanelLogin() {

            }




        }


         class PanelRegister extends JPanel {


            public PanelRegister() {
                this.setLayout(null);

                JLabel label_name = new JLabel("账户名:");
                label_name.setBounds(50,40,50,30);
                this.add(label_name);
                JTextField text_name = new JTextField();
                text_name.setBounds(150,40,300,30);
                this.add(text_name);

                JLabel label_pass=new JLabel("密码:");
                label_pass.setBounds(50,80,50,30);
                this.add(label_pass);
                JPasswordField text_pass=new JPasswordField();
                text_pass.setBounds(150,80,300,30);
                this.add(text_pass);

                JLabel Pass_confirm=new JLabel("确认密码:");
                Pass_confirm.setBounds(50,120,100,30);
                this.add(Pass_confirm);
                JPasswordField text_passc=new JPasswordField();
                text_passc.setBounds(150,120,300,30);
                this.add(text_passc);

                JLabel label_question = new JLabel("请设置安全问题:");
                label_question.setBounds(50,160,200,30);
                this.add(label_question);
                JComboBox<String> comboBox_question=new JComboBox<String>();
                String[] select= {"你就读的专业是？","你最擅长的编程语言是？","你的爱好是？"};
                comboBox_question.setModel(new DefaultComboBoxModel(select));
                comboBox_question.setBounds(150,160,300,30);
                this.add(comboBox_question);

                JLabel label_answer=new JLabel("答案:");
                label_answer.setBounds(50,200,50,30);
                this.add(label_answer);
                JTextField text_answer=new JTextField();
                text_answer.setBounds(150,200,300,30);
                this.add(text_answer);


                JButton btn_register=new JButton("注册");
                btn_register.setBounds(230, 250, 100, 30);
                btn_register.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        //TODO


                    }
                });

                this.add(btn_register);


            }



        }



        class PanelReset extends JPanel {


            public PanelReset() {

            }


        }




}
