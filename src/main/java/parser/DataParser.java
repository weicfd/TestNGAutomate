package parser;

import generator.Config;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import fsm.DataCodeConverter;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataParser {
    private static String dataPath = "/tmp/hashmap.ser";

    public DataParser() {
        // save data to one file
        HashMap<Long, String> hmap = new HashMap<Long, String>();
        Document document = null;

        try
        {
            SAXReader saxReader = new SAXReader();
            File services_dir = new File(Config.data_xml_dir);
            for (File service :
                    services_dir.listFiles()) {
                String service_name = service.getName();
                if (!service.isDirectory())
                    continue;
                if (service.listFiles() == null) {
                    System.out.println("No files under " + service.getPath());
                    System.exit(0);
                }

                // todo: 暂时只用add下的数据，其他数据以后再改
                for (File temp :
                        service.listFiles()) {
                    if (temp.isDirectory() && temp.getName().equals("add")) {
                        for (File temp2 :
                                temp.listFiles()) {
                            if (!temp2.isDirectory())
                                continue;
                            for (File data :
                                    temp2.listFiles()) {
                                document = saxReader.read(data); // 读取XML文件,获得document对象
                                Element param = document.getRootElement();
                                // map存成的格式是code - value
                                for (Iterator<Element> it = param.elementIterator(); it.hasNext();) {
                                    Element element = it.next();
                                    String attr = element.attributeValue("attribute");
                                    long half_code = DataCodeConverter.castAttrToCode(service_name, attr);
                                    int no = 1;
                                    for(Iterator<Element> it2 = element.elementIterator(); it2.hasNext();) {
                                        Element value = it2.next();
//                                        System.out.println("No " + no + " data: " + value.getText());
                                        hmap.put(half_code + (no++), value.getText());
                                    }
                                }

                            }
                        }
                    }

                }

            }

            FileOutputStream fos =
                    new FileOutputStream(dataPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hmap);
            oos.close();
            fos.close();
            System.out.println("Serialized HashMap data is saved in " + dataPath);
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, String> getDataCode() {
        HashMap<Integer, String> map = null;
        try
        {
            FileInputStream fis = new FileInputStream(dataPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap<Integer, String>) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        System.out.println("Deserialized HashMap..");
        return map;
        // Display content using Iterator
//        Set set = map.entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()) {
//            Map.Entry mentry = (Map.Entry)iterator.next();
//            System.out.print("key: "+ mentry.getKey() + " & Value: ");
//            System.out.println(mentry.getValue());
//        }
    }

    public static void main(String[] args) {
        // 单元测试
        dataPath = "/Users/tangmh/Desktop/paper/TestNGAutomate/src/test/data_out/hashmap.ser";
        DataParser dataParser = new DataParser();
        Map map = dataParser.getDataCode();

//         Display content using Iterator
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
//            System.out.print("key: "+ mentry.getKey() + " & Value: ");
//            System.out.println(mentry.getValue());
        }
    }

}
