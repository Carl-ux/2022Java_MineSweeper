package Service;

import game.MineArea;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

public class GameWin extends Component {
    Records records = new Records();
    String name;
    String time_record;
    String IP;
    String time_win;
    public GameWin(String name,String time_win)
    {
        JOptionPane.showMessageDialog(GameWin.this, "你扫除了所有炸弹！", "游戏胜利", JOptionPane.INFORMATION_MESSAGE);
        this.name = name;
        this.time_win = time_win;
        this.time_record = get_time_record();
        this.IP = get_IP();

        records.addRecord(name,time_record,IP,time_win);

    }

    public String get_time_record()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        return df.format(date);
    }

    public String get_IP()
    {
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
