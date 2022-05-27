/*
File:Charts.java
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

    }











    class Info {
        String name;
        int IP;
        int Time_Game;
        int Time_Login;

        public Info(String name,int IP,int Time_Game,int Time_Login) {
            this.name = name;
            this.IP = IP;
            this.Time_Game = Time_Game;
            this.Time_Login = Time_Login;
        }
    }
    //TODO Load ranking info from rank.xml into List
    private void loadInfo() {

    }



}
