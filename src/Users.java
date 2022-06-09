/*
File:Users.java
Author: Carl
Date:2022-05-27
Description:封装了对存储注册用户信息的XML文件的各种操作
LastEditor:
*/
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;



public class Users {
    Document doc;                           //DOM解析XML文件得到的Document
    NodeList userlist;                      //user_node List
    private String DB_PATH = "data/users.xml";      //File Path for users.xml

    //Init and parse into DOM tree
    public Users(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(DB_PATH);
            userlist = doc.getElementsByTagName("USER");
        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    //new user
    public void addUser(int id,String name,String password,String safety_q,String answer) {
        password = MD5.md5(MD5.salt + password);
        answer = MD5.md5(MD5.salt + answer);                        //加密

        Element element = doc.createElement("USER");                //org.w3c.dom.Element API

        Element child_element = doc.createElement("ID");            //child node
        child_element.appendChild(doc.createTextNode(String.valueOf(id)));   //text node
        element.appendChild(child_element);

        child_element=doc.createElement("NAME");
        child_element.appendChild(doc.createTextNode(name));
        element.appendChild(child_element);

        child_element=doc.createElement("PASSWORD");
        child_element.appendChild(doc.createTextNode(password));
        element.appendChild(child_element);

        child_element=doc.createElement("SAFETY_QUESTION");
        child_element.appendChild(doc.createTextNode(safety_q));
        element.appendChild(child_element);

        child_element=doc.createElement("ANSWER");
        child_element.appendChild(doc.createTextNode(answer));
        element.appendChild(child_element);

        //Add Node to Parent Node
        doc.getElementsByTagName("USERS").item(0).appendChild(element);


        //声明一个制造转换器的工厂
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
        //获取转换器对象
            Transformer transformer=transformerFactory.newTransformer();
        //把内存中的DOM树更新到硬盘中
            transformer.transform(new DOMSource(doc), new StreamResult(DB_PATH));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    //judge whether the User_name exists or not
    public boolean isExist(String name) {
        for(int i = 0;i < userlist.getLength(); i++) {
            Node user = userlist.item(i);
            if(name.equals(user.getChildNodes().item(1).getTextContent()))
                return true;
        }
        return false;
    }

    //get number of user
    public int getUserCount() {
        return userlist.getLength();
    }

    //验证账号密码
    public String authentication(int ID,String pass)
    {
        pass = MD5.md5(MD5.salt + pass);
        for (int i = 0;i < userlist.getLength();i++)
        {
            Node user = userlist.item(i);
            if(ID == Integer.parseInt(user.getChildNodes().item(0).getTextContent())
            && pass.equals(user.getChildNodes().item(2).getTextContent()))
                return user.getChildNodes().item(1).getTextContent();
        }
        return null;
    }



}

//实现md5加密
class MD5 {
    public static String salt="asdfji1SDxx)(^5@!*&^>?|00xxSAD1x";	//硬编码的盐值，防止md5被彩虹表破解

    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}

