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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
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
            userlist = doc.getElementsByTagName("user");
        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }



}
