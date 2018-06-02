package tmp.template;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import tmp.Driver;

public class addSeller1 {
    Driver driver;

public addSeller1() {
    driver = new Driver();
}

@Test(groups = { "Suc"})
public void getPattern1_Succ() throws IOException{
     String rootURL = "";

String pattern = "AF";
Reporter.log("Test Pattern: " + pattern);

String method1 = "AddSeller";
Reporter.log("Test Method: " + AddSeller);
String param1 = "productType=_y_&personincharges=null&company=K__8_OX1U&mobilePhone=18807820150&name=GS_";
Reporter.log("Test parameters: " + (param1.length() == 0 ? "None" : param1));

String response1 = driver.requestAndReturn(rootURL + method1 + '/' + param1);

String pattern = "AF";
Reporter.log("Test Pattern: " + pattern);

String method2 = "AddSeller";
Reporter.log("Test Method: " + AddSeller);
String param2 = "productType=_y_&personincharges=null&company=K__8_OX1U&mobilePhone=18807820150&name=GS_";
Reporter.log("Test parameters: " + (param2.length() == 0 ? "None" : param2));

String response2 = driver.requestAndReturn(rootURL + method2 + '/' + param2);



Assert.assertEquals(response2, ${oracle});
}
}
