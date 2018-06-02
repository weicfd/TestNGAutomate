package testng_version.template;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import testng_version.Driver;

public class IAdministerSellerManageService0 {
    Driver driver;


/**
* generated from script file: src/test/script/IAdministerSellerManageService/0/0.xml
*/
public IAdministerSellerManageService0() {
    driver = new Driver();
}

@Test(groups = { "Suc"})
public void getPattern1_Succ() throws IOException{
     String rootURL = "http://localhost:8080/GuangDa/";

String pattern = "ADD - FIND";
Reporter.log("Test Pattern: " + pattern);

String method1 = "addSeller";
Reporter.log("Test Method: " + addSeller);
String param1 = "productType=_y_&personincharges=null&company=K__8_OX1U&mobilePhone=18807820150&name=GS_&materialinformations=null&telephone=027-4748613-9&receivermapnotices=null&pathType=3J&qq=62670&forbid=kHM__2_5&products=null&traffics=null&orders=null&email=3a7lU5-w7rPh333@L-__-.gi5_I2i53&sellerID=-2&passWord=KugM975F4C___dG1_7&businessLicense=GM3n4_d27H4&loginName=7HX";
Reporter.log("Test parameters: " + (param1.length() == 0 ? "None" : param1));

String response1 = driver.httpConnect(rootURL + method1 + '/' + param1);
Reporter.log("Test Response: " + response1);
    String method2 = "getSellerByID";
Reporter.log("Test Method: " + getSellerByID);
String param2 = "productType=_y_&personincharges=null&company=K__8_OX1U&mobilePhone=18807820150&name=GS_&materialinformations=null&telephone=027-4748613-9&receivermapnotices=null&pathType=3J&qq=62670&forbid=kHM__2_5&products=null&traffics=null&orders=null&email=3a7lU5-w7rPh333@L-__-.gi5_I2i53&sellerID=-2&passWord=KugM975F4C___dG1_7&businessLicense=GM3n4_d27H4&loginName=7HX";
Reporter.log("Test parameters: " + (param2.length() == 0 ? "None" : param2));

String response2 = driver.httpConnect(rootURL + method2 + '/' + param2);
Reporter.log("Test Response: " + response2);
    
String responseText = response2;
    Assert.assertEquals(driver.getJsonValue(responseText, productType), "_y_");
    Assert.assertEquals(driver.getJsonValue(responseText, personincharges), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, company), "K__8_OX1U");
    Assert.assertEquals(driver.getJsonValue(responseText, mobilePhone), "18807820150");
    Assert.assertEquals(driver.getJsonValue(responseText, name), "GS_");
    Assert.assertEquals(driver.getJsonValue(responseText, materialinformations), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, telephone), "027-4748613-9");
    Assert.assertEquals(driver.getJsonValue(responseText, receivermapnotices), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, pathType), "3J");
    Assert.assertEquals(driver.getJsonValue(responseText, qq), "62670");
    Assert.assertEquals(driver.getJsonValue(responseText, forbid), "kHM__2_5");
    Assert.assertEquals(driver.getJsonValue(responseText, products), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, traffics), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, orders), "null");
    Assert.assertEquals(driver.getJsonValue(responseText, email), "3a7lU5-w7rPh333@L-__-.gi5_I2i53");
    Assert.assertEquals(driver.getJsonValue(responseText, idseller), "-2");
    Assert.assertEquals(driver.getJsonValue(responseText, passWord), "KugM975F4C___dG1_7");
    Assert.assertEquals(driver.getJsonValue(responseText, businessLicense), "GM3n4_d27H4");
    Assert.assertEquals(driver.getJsonValue(responseText, loginName), "7HX");

}
}
