import entity.Case;
import generator.GenerateTest;
import parser.MethodsParser;

import java.io.File;

public class BasicTest {
    public static void main(String[] args) {
        GenerateTest generateTest = new GenerateTest();
        File script = new File("src/test/script/IAdministerSellerManageService/0/0.xml");
        System.out.println("load script :" + script.getPath());
        MethodsParser parser = new MethodsParser(script);
        Case c = parser.getCase();
        System.out.println("Generate test: ");
        System.out.println(c);
        generateTest.generateTest(c.getMethods(), c.getServiceName(), c.getPat(), c.getOracle(), script.getPath());
    }
}
