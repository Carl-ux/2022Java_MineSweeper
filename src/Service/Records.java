package Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class Records {
    Document doc;                                    //DOM解析XML文件得到的Document
    NodeList recordlist;                               //user_node List
    private String DB_PATH = "data/rank.xml";        //File Path for users.xml

    //Init and parse into DOM tree
    public Records() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(DB_PATH);
            recordlist = doc.getElementsByTagName("RECORD");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }


    //get number of records
    public int getRecordCount() {
        return recordlist.getLength();
    }

    public String getName(int index)
    {
        Node record = recordlist.item(index);
        return record.getChildNodes().item(0).getTextContent();
    }

    public String getTimeRecord(int index)
    {
        Node record = recordlist.item(index);
        return record.getChildNodes().item(1).getTextContent();
    }

    public String getIP(int index)
    {
        Node record = recordlist.item(index);
        return record.getChildNodes().item(2).getTextContent();
    }

    public String getTimeWin(int index)
    {
        Node record = recordlist.item(index);
        return record.getChildNodes().item(3).getTextContent();
    }

    public void addRecord(String name,String time_record,String IP,String time_win)
    {
        Element element = doc.createElement("RECORD");

        Element child_element = doc.createElement("User_Name");
        child_element.appendChild(doc.createTextNode(name));
        element.appendChild(child_element);

        child_element = doc.createElement("Time_Record");
        child_element.appendChild(doc.createTextNode(time_record));
        element.appendChild(child_element);

        child_element = doc.createElement("User_IP");
        child_element.appendChild(doc.createTextNode(IP));
        element.appendChild(child_element);

        child_element = doc.createElement("Time_Win");
        child_element.appendChild(doc.createTextNode(time_win));
        element.appendChild(child_element);

        doc.getElementsByTagName("RECORDS").item(0).appendChild(element);

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



}
