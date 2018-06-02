package template;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import testng_version.Driver;

public class exampleTest {
    Driver driver;

    public exampleTest() {
        driver = new Driver();
    }

    @Test(groups = { "BaseCase"})
    public void getPattern1_Succ() throws IOException{
        String rootURL = "https://localhost:8080/";

        String pattern = "AUF";
        Reporter.log("Test Pattern：" + pattern);

        String method1 = "AddPathType";
        Reporter.log("Test Method：" + method1);
        String param1 = "productType=AAA1";
        Reporter.log("Test parameters:" + (param1.length() == 0 ? "None" : param1));
        String target1 = "name";

        String response1 = driver.requestAndReturn(rootURL + method1 + '/' + param1 , target1);

        // TODO: is there any median result check or not?

        String method2 = "modifyPathType";
        Reporter.log("Test Method：" + method2);
        String param2 = "";
        Reporter.log("Test parameters:" + (param2.length() == 0 ? "None" : param2));
        String target2 = "name";

        String response2 = driver.requestAndReturn(rootURL + method2 + '/' + param2 , target2);


        // oracle predict
        // TODO:  Oracle predict
        String oracle = "";
//        oracle = predictOracleFSM(); generate when using template

        Assert.assertEquals(response2, oracle);
    }

    private String convertToParamUrl(Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (paramsMap.isEmpty()) {
            return "";
        }

        for (Iterator<String> iterator = paramsMap.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            stringBuilder.append(key);
            stringBuilder.append('=');
            stringBuilder.append(paramsMap.get(key));
            if (iterator.hasNext()) stringBuilder.append('&');
        }

        return stringBuilder.toString();
    }

    public void resultCheck(String param) throws IOException{
        Reporter.log("");
        Assert.assertEquals("", "");
    }
}
