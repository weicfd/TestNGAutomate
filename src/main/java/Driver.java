
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Driver {
    public  String requestAndReturn(String url, String resultID) {
        // url include the API url and the param which contain the data

        // make connection to http
        // parse the result (make sure the result type!)
        // default: JSON
        return getJsonValue(httpConnect(url), resultID);
    }

    /**
     * 使用HTTP协议将请求发送到被测系统并获取返回报文内容
     * @param url 需要发送请求的URL
     * @return 响应报文内容
     */
    public static String httpConnect(String url) {
        String line = "";
        StringBuilder httpResults = new StringBuilder();
        try {
            HttpURLConnection connection = null;
            URL postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            // 设置通用的请求属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");

            DataOutputStream out = null;
            // 建立实际的连接
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                httpResults.append(line);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpResults.toString();
    }


    /**
     * 解析Json内容
     * @param JsonString 需要解析的JSON内容
     * @param JsonId 目标内容的JSON ID
     * @return JsonValue 返回JsonString中JsonId对应的Value
     */
    public static String getJsonValue(String JsonString, String JsonId) {
        String JsonValue = "";
        if (JsonString == null || JsonString.trim().length() < 1) {
            return null;
        }
        try {
            JSONObject obj1 = new JSONObject(JsonString);
            JsonValue = (String) obj1.getString(JsonId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonValue;
    }

}
