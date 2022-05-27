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
        this.setSize(500, 350);
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

            }



        }



        class PanelReset extends JPanel {


            public PanelReset() {

            }


        }




}
