package generator;

import entity.Case;
import entity.Method;
import entity.Service;
import fsm.DataCodeConverter;
import parser.DataParser;
import parser.MethodsParser;
import parser.PatternParser;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static generator.Config.generate_test_dir;

public class GenerateTest {
    private DataParser dataParser;
    private HashMap<Integer, String> dataMap;
//    private PatternParser patternParser;
//    private List<String> patterns;
//    private List<Service> services;

    private static int testFileID = 0;

    public GenerateTest() {
        // parse dataMap
        dataParser = new DataParser();
        dataMap = dataParser.getDataCode(); // for the update method to modify this step

//        File service_dir = new File(service_xml_dir);
//        String[] service_paths = service_dir.list();
//
//        if (service_paths == null) {
//            System.err.println("service xml dir config error");
//            System.exit(0);
//        }
//
//        for (String service_name :
//                service_paths) {
//            services.add(new MethodsParser(service_name).getService());
//        }
//
//        // parse patterns
//        patternParser = new PatternParser(pattern_file_path);
//        patterns = patternParser.getPatterns(); // for the update method to modify this step
    }

    public static void main(String[] args) {
//        System.out.println("Step 1: initialize the dataMap, services and pattern");
        System.out.println("Step 1: initialize the dataMap");
        GenerateTest generateTest = new GenerateTest();

        System.out.println("Step 2: For each service, get script file ordered by pat");
        File services = new File(Config.script_xml_dir);
        if (!services.isDirectory()) System.err.println("script xml dir path error");
        for (File service :
                services.listFiles()) {
            if (!service.isDirectory()) continue;
            File[] pats = service.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });

//            System.out.println(Arrays.toString(pats));
            // sort by pattern length
            if (pats == null) continue;
            Arrays.sort(pats, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Integer.valueOf(o1.getName()) - Integer.valueOf(o2.getName());
                }
            });

            for (int i = 0; i < pats.length; i++) {
                if (!pats[i].isDirectory() || pats[i].listFiles() == null) continue;
                for (File script :
                        pats[i].listFiles()) {
                    if (script.isFile()) {
                        System.out.println("load script :" + script.getPath());
                        MethodsParser parser = new MethodsParser(script);
                        Case c = parser.getCase();
                        System.out.println("Generate test: ");
                        System.out.println(c);
                        generateTest.generateTest(c.getMethods(), c.getServiceName(), c.getPat(), c.getOracle(), script.getPath());

                    }
                }
            }
        }
//        System.out.println("Step 2: for each pattern, generate several testNG class files as one test");
//        // sort the patterns first
//        Collections.sort(generateTest.patterns);
//
//        System.out.println("Step 3: Pattern - Methods Mapping");
//        // group by service
//        for (Service s :
//                generateTest.services) {
//            for (String pat :
//                    generateTest.patterns) {
//                List<Method> testPath = new ArrayList<>();
//                generateTest.testPathDFS(0, testPath, pat, s);
//            }
//        }



    }
//
//    private void testPathDFS(int loc, List<Method> testPath, String pat, Service s, Case c) {
//        if (loc == pat.length())
//            generateTest(testPath, s.getService_name(), pat, c.getOracle(), c.getPath());
//        else {
//            List<Method> methods = s.getMethods(pat.charAt(loc) - '0');
//            for (Method method:
//                    methods) {
//                testPath.add(method);
//                testPathDFS(loc + 1, testPath, pat, s, c);
//                testPath.remove(testPath.size() - 1);
//            }
//        }
//    }

    public void generateTest(List<Method> testPath, String service_name, String pat, Map<Long, String> oracle, String script) {
        String targetPath = generate_test_dir + service_name;
        File targetDir = new File(targetPath);
        if (!targetDir.exists()) targetDir.mkdirs();

        System.out.println("Step 3: scan the target method with locator param");
        int[] cases = DataCodeConverter.getCases(testPath);
        for (int i = 0; i < cases.length; i++) {
            TemplateWriter writer1 = new TemplateWriter(testFileID++, testPath, service_name, cases[i], pat, oracle, dataMap, script);
        }
//        TemplateWriter writer1 = new TemplateWriter(testFileID++, testPath, service_name, true, pat, dataMap);
//        TemplateWriter writer2 = new TemplateWriter(testFileID++, testPath, service_name, false, pat, dataMap);
    }

}
