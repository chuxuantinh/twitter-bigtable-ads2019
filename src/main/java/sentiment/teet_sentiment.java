package sentiment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class teet_sentiment {

    private static final String USER_AGENT = "Mozilla/5.0";
    public static void main(String[] args){
        try {
            URL url=new URL("http://www.sentiment140.com/api/bulkClassifyJson");
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);

            String urlParameters = "{\"data\": [{\"text\": \"I love iphone11.\", \"query\": \"iphone11\"},{\"text\": \"I hate iphone11 .\", \"query\": \"hate\"}]}";
            String urlParameters2  ="{\"data\": [{\"text\": \"I love Titanic.\", \"query\": \"Titanic\", \"topic\": \"movies\"}, \n" +
                    "          {\"text\": \"I hate Titanic.\", \"query\": \"Titanic\", \"topic\": \"movies\"}]}";
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.writeBytes(urlParameters2);
            wr.flush();
            wr.close();

            //out
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            //System.out.println("Post parameters : " + urlParameters2);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}