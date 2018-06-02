package generator;

import entity.Method;
import fsm.DataFsm;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateWriter {
    VelocityEngine ve;
    String root_dir = Config.generate_test_dir;
    HashMap<Integer, String> dataMap;
    DataFsm dataFsm;
    List<Method> methods;


    public TemplateWriter(int fileID, List<Method> methods, String serviceName, int caseNo, String pat, Map<Long, String> target, HashMap<Integer, String> dataMap, String path) {
        this.dataMap = dataMap;
        dataFsm = DataFsm.getDataFsm();
        dataFsm.init(methods, caseNo);
//        System.out.println("CaseNo: " + caseNo);
        this.methods = methods;

        //处理中文问题
        //        ve.setProperty(Velocity.INPUT_ENCODING,"GBK");
        //
        //        ve.setProperty(Velocity.OUTPUT_ENCODING,"GBK");

        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        VelocityContext root = new VelocityContext();

        //把数据填入上下文
        root.put("package_dir", Config.project_package_dir);
        root.put("test_naming", serviceName + fileID);
        root.put("root_url", Config.project_root_url);
        root.put("pattern", translatePattern(pat));
        root.put("script_name", path);

        // method list, param list, target list
        int len = methods.size();
        String[] methodList = new String[len];
        String[] paramList = new String[len];
        String[] targetList = new String[target.size()];

        for (int i = 0; i < len; i++) {
            Method m = methods.get(i);
//            System.out.println(m);
            methodList[i] = m.getMethodName();
            paramList[i] = getData(m.getDataCodeMap(), m.getmType());
            if (i < len - 1) dataFsm.next();
        }

        Map<String, String> oracle = new HashMap<>();
        int i = 0;
        for (Long code :
                target.keySet()) {
            targetList[i] = target.get(code);
            String value = dataMap.get(dataFsm.getOracle(code));
            if (value == null) value = "null";
            oracle.put(targetList[i], value);
            i++;
        }
        root.put("methodList", methodList);
        System.out.println("methodList \n" + Arrays.toString(methodList));
        root.put("paramList", paramList);
        System.out.println("paramList \n" + Arrays.toString(paramList));
        root.put("targetList", targetList);
        root.put("group_naming", (caseNo == 2 || caseNo == 5 || caseNo == 8)? "Fail" : "Suc");

        root.put("oracle", oracle);

        Template template = null;

        try
        {
            template = Velocity.getTemplate("biscTest.vm");
            String outpath = root_dir + serviceName + '/' + serviceName + fileID + ".java";
            //输出
            System.out.println("File generated in :" + outpath);
            Writer mywriter = new PrintWriter(new FileOutputStream(
                    new File(outpath)));
            template.merge(root, mywriter);
            mywriter.flush();
        }
        catch( ResourceNotFoundException rnfe )
        {
            System.out.println("couldn't find the template");
        }
        catch( ParseErrorException pee )
        {
            System.out.println("syntax error: problem parsing the template");
        }
        catch( MethodInvocationException mie )
        {
            // something invoked in the template
            // threw an exception
        }
        catch( Exception e )
        {}

    }

    private String translatePattern(String pat) {
        StringBuilder builder = new StringBuilder();
        String con = "";
        for (int i = 0; i < pat.length(); i++) {
            builder.append(con);
            switch (pat.charAt(i) - '0') {
                case 1:
                    builder.append("ADD");
                    break;
                case 2:
                    builder.append("UPDATE");
                    break;
                case 3:
                    builder.append("DELETE");
                    break;
                case 4:
                    builder.append("FIND");
                    break;
                default:
                    builder.append("UNKNOWN");
                    break;
            }
            con = " - ";
        }
        return builder.toString();
    }

    private String merge(List<String> targets) {
        StringBuilder builder = new StringBuilder();
        String con = "";
        for (String t :
                targets) {
            builder.append(con);
            con = "_"; // @note the connection is underline
            builder.append(t);
        }
        return builder.toString();
    }

    private String getData(Map<Long, String> dataCodeSet, int mType) {
        StringBuilder urlBuilder = new StringBuilder();
        String con = "";
        if (!dataCodeSet.isEmpty()) {
            for (long code :
                    dataCodeSet.keySet()) {
                urlBuilder.append(con);
                con = "&";
                urlBuilder.append(dataCodeSet.get(code));
                urlBuilder.append('=');
                // TODO: 18/5/11 dataMap generate fsm (design a new module)
                urlBuilder.append(dataMap.get(dataFsm.getData(code)));
            }
        }
        return urlBuilder.toString();
    }

    public static void main(String[] args) {
        // unit test

        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        VelocityContext root = new VelocityContext();

        root.put("package_dir", "tmp");
        root.put("test_naming", "addSeller" + 1);
        root.put("root_url", Config.project_root_url);
        root.put("pattern", "AF");

        int len = 2;
        String[] methodList = new String[len];
        String[] paramList = new String[len];

        for (int i = 0; i < len; i++) {
            methodList[i] = "AddSeller";
            paramList[i] = "productType=_y_&personincharges=null&company=K__8_OX1U&mobilePhone=18807820150&name=GS_";
        }
        root.put("methodList", methodList);
//        System.out.println("methodList \n" + Arrays.toString(methodList));
        root.put("paramList", paramList);
//        System.out.println("paramList \n" + Arrays.toString(paramList));
//            root.put("targetList", targetList);
        root.put("group_naming", "Suc");


        Template template = null;

        try
        {
            template = Velocity.getTemplate("biscTest.vm");
            String outpath = "/Users/tangmh/Desktop/paper/TestNGAutomate/src/test/template/tmp/" + "addSeller" + 1 + ".java";
            //输出
            Writer mywriter = new PrintWriter(new FileOutputStream(
                    new File(outpath)));
            template.merge(root, mywriter);
            mywriter.flush();
        }
        catch( ResourceNotFoundException rnfe )
        {
            System.out.println("couldn't find the template");
        }
        catch( ParseErrorException pee )
        {
            System.out.println("syntax error: problem parsing the template");
        }
        catch( MethodInvocationException mie )
        {
            // something invoked in the template
            // threw an exception
        }
        catch( Exception e )
        {}
    }
}
