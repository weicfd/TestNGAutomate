package parser;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jaxen.*; // 采用xpath查找需要引入jaxen-xx-xx.jar，否则会报java.lang.NoClassDefFoundError: org/jaxen/JaxenException异常
import entity.Case;
import entity.Method;
import fsm.DataCodeConverter;

import java.io.File;
import java.util.*;

public class MethodsParser {
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
//    Service service;
    Case aCase;
//    String methodName;
//    String entityName; // used to find the related methods, 暂不考虑一个method对应多个entity的情况
    Map<Long, String> dataCodeMap; // parameter
    Map<Long, String> targetCodeMap;

    public MethodsParser(File resource) {
        // use dom4j to parse it
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(resource); // 读取XML文件,获得document对象
            Element script = document.getRootElement();
            String serviceName = script.attributeValue("resourcesID");
//            service = new Service(serviceName);
            long locatorCode = 0;
            dataCodeMap = new HashMap<>();
            targetCodeMap = new HashMap<>();

            // read the info of method
            List<Method> methods = new ArrayList<>();
            StringBuilder pat = new StringBuilder();
            // methodID entityCode and dataCodeList
            for (Iterator<Element> iterator = script.elements().iterator(); iterator.hasNext();) { // iterator method
                Element method = iterator.next();
                String mName = method.attributeValue("path");

                if (method.element("param") == null && method.element("response") == null) continue; // 无参数的操作
                // 参数读取 param - i-element
                List<Node> params = method.selectNodes("param");
                for (Node paramnode :
                        params) {
                    Element param = (Element)paramnode;
                    if (param.attributeValue("attribute").split("\\.").length == 1) {
                        // 若为复合变量
                        List<Node> elements = param.selectNodes("element1");
                        for (Node elementNode :
                                elements) {
                            // TODO: 18/5/11 deal with the problem of level
                            Element element = (Element)elementNode;
//                    System.out.println("convert " + serviceName + ", " + element.attributeValue("attribute"));
                            Long code = DataCodeConverter.castAttrToCode(serviceName, element.attributeValue("attribute"));
                            dataCodeMap.put(code, element.attributeValue("name"));

                            if (!param.attributeValue("location").equals("false")) {
                                locatorCode = DataCodeConverter.castAttrToCode(serviceName, method.element("param").attributeValue("location"));
                            }
                        }
                    } else {
                        // single param
                        if (!param.attributeValue("location").equals("false")) {
                            locatorCode = DataCodeConverter.castAttrToCode(serviceName, method.element("param").attributeValue("location"));
                        }
//                    System.out.println("convert " + serviceName + ", " + element.attributeValue("attribute"));
                        Long code = DataCodeConverter.castAttrToCode(serviceName, param.attributeValue("attribute"));
                        dataCodeMap.put(code, param.attributeValue("name"));

                    }
                }

                // todo: codeSet need the parameter name?

//                service.addMethod(new Method(mName, convertAction(method.attributeValue("operation")), dataCodeMap, locatorCode));
                int mAction = convertAction(method.attributeValue("operation"));
                pat.append(mAction);
                methods.add(new Method(mName, mAction, dataCodeMap, locatorCode));
                if (!iterator.hasNext()) {
                    // the last method
                    if (mAction != mFIND) System.err.println("Script not end with FIND");
                    List<Node> targets = method.element("response").elements("param");
                    for (Node targetNode :
                            targets) {
                        Element target = (Element) targetNode;
                        String targetAttr = target.attributeValue("attribute");
                        if (targetAttr == null) {
                            // raw input
                            System.err.println("Error: Attr is empty");
                            // TODO: 18/6/1 empty attr : deal with raw data type
                        }
                        else if (targetAttr.split("\\.").length == 1) {
                            // 若为复合变量
                            List<Node> elements = target.selectNodes("element1");
                            // TODO: deal with the problem of level
                            for (Node elementNode :
                                    elements) {
                                // TODO: 18/5/11 deal with the problem of level
                                Element element = (Element)elementNode;
                                Long code = DataCodeConverter.castAttrToCode(serviceName, element.attributeValue("attribute"));
                                targetCodeMap.put(code, element.attributeValue("name"));
                            }
                        } else {
                            // single param
                            Long code = DataCodeConverter.castAttrToCode(serviceName, target.attributeValue("attribute"));
                            dataCodeMap.put(code, target.attributeValue("name"));
                        }
                    }
                }
            }


            aCase = new Case(methods, serviceName, pat.toString(), targetCodeMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private int convertAction(String name) {
        switch (name) {
            case "add":
                return mADD;
            case "update":
                return mUPDATE;
            case "delete":
                return mDELETE;
            case "find":
                return mFIND;
        }
        return mUNKNOWN;
    }

//    public Service getService() {
//        return service;
//    }

    public Case getCase() {
        return aCase;
    }

    public static void main(String[] args) {
        // 单元测试
        File dir = new File("src/test/script/IAdministerSellerManageService/0");
        for (File file:
             dir.listFiles()) {
            if (!file.getName().endsWith("xml")) continue;
            MethodsParser parser = new MethodsParser(file);
            System.out.println(parser.getCase());
            System.out.println("");
        }

    }


}
