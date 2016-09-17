package mifi.pm.com.exampleproject.handler;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Created by Purnendu Mishra on 9/7/2016.
 */
public class HttpRequest {

    private static XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public static String ReuestOnConnect(){
       String result = null;
        try {
            URL url = new URL("http://1.254.254.254");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream stream = conn.getInputStream();
        /*    xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream,null);
            parseXMLAndStoreIt(myparser);*/
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            br = new BufferedReader(new InputStreamReader(stream));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            stream.close();
            Log.e("TAG","Response"+sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

       /* try{
            InputStream is = new ByteArrayInputStream(str.getBytes());
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(is,null);

            parseXMLAndStoreIt(myparser);
            is.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        return result;
    }
    public static String RequestLogin(String username,String password){
        StringBuilder sb = new StringBuilder();
        String result = null;
        try {
            URL url = new URL("http://10.130.204.24/userportal/NSCLOGIN.do?requesturi=http%3a%2f%2f1%2e254%2e254%2e254%2f&ip=100%2e72%2e112%2e54&mac=f4%3a8b%3a32%3a1f%3ae3%3aad&nas=mts&requestip=1%2e254%2e254%2e254&sc=3ed284ac5e0f5b6b3eaaa956b845b6fd&username="+username+"&password="+password);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream stream = conn.getInputStream();
           /* xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream,null);
            parseXMLAndStoreIt(myparser);*/
            BufferedReader br = null;

            String line;
            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            stream.close();
            Log.e("TAG","Response"+sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
public static String RequestRegister(String firstname,String lastname,String username,String email,String num){
    StringBuilder sb = new StringBuilder();
    try {
        URL url = new URL("http://10.130.204.24/userportal/pages/usermedia/mts/scripts/registration.jsp&fname="+firstname+"&lname="+lastname+"&email="+email+"&address=Gurgaon&mobile="+num);
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody =  new FormEncodingBuilder()
                .add("fname", firstname)
                .add("lname", lastname)
                .add("email", email)
                .add("address", num)
                //.add("mobile", "Your message")
                .build();
        Request request = new Request.Builder()
                .url("http://10.130.204.24/userportal/pages/usermedia/mts/scripts/registration.jsp")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
sb.append(response.body().toString());
            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    catch (Exception e) {
        e.printStackTrace();
    }

    return sb.toString();
}
    public static String RequestOtp(String otp){
        StringBuilder sb = new StringBuilder();
        try {
          //  URL url = new URL("http://10.130.204.24/userportal/pages/usermedia/mts/scripts/otpverification.jsp&otp="+otp);
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody =  new FormEncodingBuilder()
                    .add("otp", otp)

                    .build();
            Request request = new Request.Builder()
                    .url("http://10.130.204.24/userportal/pages/usermedia/mts/scripts/otpverification.jsp")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                sb.append(response.body().toString());
                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("TAG","Response"+sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static String SendRequestToGST(String json){
          final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        StringBuilder sb=new StringBuilder();
        OkHttpClient client = new OkHttpClient();

       /* RequestBody formBody =  new FormEncodingBuilder()
                .add("fname", gcmid)
                .add("lname", deviceid)
                .add("email", macAddress)
                .add("address", DeviceModel)
                .add("history", history)
                .add("mobile", mobile)
                .build();*/
        Request request = new Request.Builder()
                .url(" http://52.66.157.34/mifi_connect/api/register")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String str = new String(response.body().bytes(), "UTF-8");
            sb.append(str);
            // Do something with the response.
            Log.e("TAG","Response"+sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static String LogOutRequest(){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://10.130.204.24/userportal/NSCLOGOUT.do?requesturi=http%3a%2f%2f1%2e254%2e254%2e254%2f&ip=100%2e72%2e112%2e54&mac=f4%3a8b%3a32%3a1f%3ae3%3aad&nas=mts&requestip=1%2e254%2e254%2e254&sc=3ed284ac5e0f5b6b3eaaa956b845b6fd");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream stream = conn.getInputStream();
           /* xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream,null);
            parseXMLAndStoreIt(myparser);*/
            BufferedReader br = null;

            String line;
            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            stream.close();
            Log.e("TAG","Response"+sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static String StatusRequest(){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://10.130.204.24/userportal/NSCSTATUS.do?requesturi=http%3a%2f%2f1%2e254%2e254%2e254%2f&ip=100%2e72%2e112%2e54&mac=f4%3a8b%3a32%3a1f%3ae3%3aad&nas=mts&requestip=1%2e254%2e254%2e254&sc=3ed284ac5e0f5b6b3eaaa956b845b6fd");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream stream = conn.getInputStream();
           /* xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream,null);
            parseXMLAndStoreIt(myparser);*/
            BufferedReader br = null;

            String line;
            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            stream.close();
            Log.e("TAG","Response LoginRequest "+sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:

                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("msc")){
                            Log.e("TAG","aaa"+text);
                        }

                        else if(name.equals("login_url")){
                           // humidity = myParser.getAttributeValue(null,"value");
                            Log.e("TAG","aaa1"+myParser.getAttributeValue(null,"value"));
                        }

                        else if(name.equals("logout_url")){

                            Log.e("TAG","aaa2"+ myParser.getAttributeValue(null,"value"));
                        }

                        else if(name.equals("status_url")){
                          //  temperature = myParser.getAttributeValue(null,"value");
                            Log.e("TAG","aaa3"+myParser.getAttributeValue(null,"value"));
                        }

                        else{
                        }
                        break;
                }
                event = myParser.next();
            }
           // parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
