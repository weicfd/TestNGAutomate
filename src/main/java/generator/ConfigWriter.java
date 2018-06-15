package generator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ConfigWriter {
    Document doc;
    Element rootElem;

    public ConfigWriter(String suiteName) {
        doc = DocumentHelper.createDocument();
        doc.addDocType("suite", null, "http://testng.org/testng-1.0.dtd");
        rootElem=doc.addElement("suite"); // 创建根标签
        rootElem.addAttribute("name", suiteName);
        rootElem.addAttribute("verbose", "1");
        Element testElem = rootElem.addElement("test");
        testElem.addAttribute("name", "Basic");
        Element packsElem = testElem.addElement("packages");
        Element packElem = packsElem.addElement("package");
        packElem.addAttribute("name", Config.project_package_dir);
        Element runElem = testElem.addElement("groups").addElement("run");
        Element group1 = runElem.addElement("include");
        group1.addAttribute("name", "Suc");
        Element group2 = runElem.addElement("include");
        group2.addAttribute("name", "Fail");

    }



    public void write() {
        // 把创建的Document对象写到xml文件
        //指定文件输出位置
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Config.config_xml_dir);
            OutputFormat format = OutputFormat.createPrettyPrint();//标准化布局，适合查看时显示。
            XMLWriter writer = new XMLWriter(out,format);
            writer.write(doc); // 写入文件
            System.out.println("testng.xml config file created at " + Config.config_xml_dir);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
