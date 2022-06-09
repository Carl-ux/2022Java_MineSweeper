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
    private int BASE_ID = 10000;

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
        jtab.add("登录", panel_login);
        jtab.add("注册", panel_register);
        this.add(jtab);
        this.setVisible(true);
    }


        //TODO 登录 注册 面板的内部实现
        class PanelLogin extends JPanel {

            public PanelLogin() {
                this.setLayout(null);
                JLabel label_account = new JLabel("ID:");
                label_account.setBounds(50,100,50,30);
                this.add(label_account);
                JTextField text_account = new JTextField();
                text_account.setBounds(150,100,300,30);
                this.add(text_account);

                JLabel label_pass=new JLabel("密码:");
                label_pass.setBounds(50,150,50,30);
                this.add(label_pass);
                JPasswordField text_pass=new JPasswordField();
                text_pass.setBounds(150,150,300,30);
                this.add(text_pass);

                JButton btn_login = new JButton("登录");
                btn_login.setBounds(230, 250, 100, 30);
                btn_login.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if(text_account.getText().isEmpty() || String.valueOf(text_pass.getPassword()).isEmpty()) {
                            JOptionPane.showMessageDialog(Account.this, "账号和密码不能为空", "登录失败", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                        {
                            String name = users.authentication(Integer.parseInt(text_account.getText()),String.valueOf(text_pass.getPassword()));
                            if(name != null)
                            {
                                Main.login(Integer.parseInt(text_account.getText()),name);
                                Account.this.dispose();
                            }
                            else
                                JOptionPane.showMessageDialog(Account.this, "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                this.add(btn_login);
            }

        }


        class PanelRegister extends JPanel {

            public PanelRegister() {
                this.setLayout(null);

                JLabel label_name = new JLabel("用户名:");
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
                        if(text_name.getText().isEmpty() || String.valueOf(text_pass.getPassword()).isEmpty()
                        || String.valueOf(text_passc.getPassword()).isEmpty() || text_answer.getText().isEmpty()) {
                            //弹窗方法
                            JOptionPane.showMessageDialog(Account.this,"用户名、密码和答案不能为空",
                                    "注册失败",JOptionPane.WARNING_MESSAGE);
                        }
                        else {
                            if(users.isExist(text_name.getText())) {
                                JOptionPane.showMessageDialog(Account.this,"用户名已存在",
                                        "注册失败",JOptionPane.ERROR_MESSAGE);
                            }
                            else if(!(String.valueOf(text_pass.getPassword()).equals(String.valueOf(text_passc.getPassword())))) {
                                JOptionPane.showMessageDialog(Account.this,"确认密码输入有误",
                                        "注册失败",JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                users.addUser(users.getUserCount() + BASE_ID,text_name.getText(),String.valueOf(text_pass.getPassword()),
                                        comboBox_question.getSelectedItem().toString(),text_answer.getText());
                                JOptionPane.showMessageDialog(Account.this,"注册成功,你的账号是" +
                                        (users.getUserCount() + BASE_ID -1),"注册成功",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                });

                this.add(btn_register);
            }
        }
}
