import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class ReadJson {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        JSONObject file = new JSONObject();
        file.put("Full Name", "categoryId");
        file.put("categoryId", ("A"));
        file.put("categoryDesc.", (1704310046));


        // To print in JSON format.
        System.out.print(file.get("categoryId"));
        System.out.print(file.get("categoryDesc"));

        new ReadJson();

    }
    public ReadJson(){
        try {
            pull();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public  void catagoryPuller() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://data.orghunter.com/v1/categories?user_key=9058ab49c4cfea622eb9a247cf35d2bb\n");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {

            String name = (String)jsonObject.get("data");

            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("data");
            int n =   msg.size(); //(msg).length();
            for (int i = 0; i < n; ++i) {
                String test =(String) msg.get(i);
                System.out.println(test);
                System.out.println();
                // System.out.println(person.getInt("key"));
            }
//            String name= (String)jsonObject.get("height");
            System.out.println(name);
        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }

    public  void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://data.orghunter.com/v1/charitysearch?user_key=9058ab49c4cfea622eb9a247cf35d2bb&searchTerm=cancer\n");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        JSONObject jsonObject = (JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {

           // String name = (String)jsonObject.get("data");

            JSONArray msg = (JSONArray) jsonObject.get("data");
            int n =   msg.size(); //(msg).length();
            for (int i = 0; i < n; ++i) {
                JSONObject test =(JSONObject) msg.get(i);
                System.out.println(test);
               
                String hb =(String) test.get("charityName");
                System.out.println(hb);
               
                String benz =(String) test.get("state");
                System.out.println(benz);
               
                String cool =(String) test.get("category");
                System.out.println(cool);
               
                String poland =(String) test.get("donationUrl");
                System.out.println(poland);

                // System.out.println(person.getInt("key"));
            }
//            String name= (String)jsonObject.get("height");
            //System.out.println(name);

        }


        catch (Exception e) {
            e.printStackTrace();
        }




    }
}


