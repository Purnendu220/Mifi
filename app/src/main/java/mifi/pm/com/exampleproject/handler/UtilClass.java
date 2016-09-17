package mifi.pm.com.exampleproject.handler;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.provider.Browser;
import android.sax.Element;
import android.telephony.TelephonyManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Purnendu Mishra on 9/4/2016.
 */
public class UtilClass {
    public static boolean  isWifiEnabled(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            return true;
        }
        else {
            return false;
        }
    }
/*    private void browserHistoryDOM() {
        try{
            File newxmlfile = new File("/sdcard/Xmlfiles/briwserHistory.xml");
            newxmlfile.createNewFile();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("root");
            document.appendChild(rootElement);

            Cursor mCur = managedQuery(Browser.BOOKMARKS_URI,Browser.HISTORY_PROJECTION, null, null, null);
            mCur.moveToFirst();

            if (mCur.moveToFirst() && mCur.getCount() > 0) {
                while (mCur.isAfterLast() == false) {
                    Element em = document.createElement("bookmarkIdx");
                    em.appendChild(document.createTextNode(mCur.getString(Browser.HISTORY_PROJECTION_BOOKMARK_INDEX)));
                    rootElement.appendChild(em);

                    long callDate = Long.parseLong(mCur.getString(Browser.HISTORY_PROJECTION_DATE_INDEX));
                    SimpleDateFormat datePattern = new SimpleDateFormat ("dd-MM-yyyy/h:m:s:a");
                    datePattern.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String date_str = datePattern.format(new Date(callDate));

                    Element em1 = (Element) document.createElement("dateIdx");
                    em1.appendChild(document.createTextNode(date_str));
                    rootElement.appendChild(em1);

                    Element em2 = (Element) document.createElement("idIdx");
                    em2.appendChild(document.createTextNode(mCur.getString(Browser.HISTORY_PROJECTION_ID_INDEX)));
                    rootElement.appendChild(em2);

                    Element em3 = document.createElement("titleIdx");
                    em3.appendChild(document.createTextNode(mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX)));
                    rootElement.appendChild(em3);

                    Element em4 = document.createElement("urlIdx");
                    em4.appendChild(document.createTextNode(mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX)));
                    rootElement.appendChild(em4);

                    Element em5 = document.createElement("visitsIdx");
                    em5.appendChild(document.createTextNode(mCur.getString(Browser.HISTORY_PROJECTION_VISITS_INDEX)));
                    rootElement.appendChild(em5);

                    long searchDate = Long.parseLong(mCur.getString(Browser.SEARCHES_PROJECTION_DATE_INDEX));
                    SimpleDateFormat datePattern1 = new SimpleDateFormat("dd-MM-yyyy/h:m:s:a");
                    datePattern1.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String date_str1 = datePattern.format(new Date(searchDate));

                    Element em6 = document.createElement("searchDateIdx");
                    em6.appendChild(document.createTextNode(date_str1));
                    rootElement.appendChild(em6);

                    Element em7 = document.createElement("searchIdx");
                    em7.appendChild(document.createTextNode(mCur.getString(Browser.SEARCHES_PROJECTION_SEARCH_INDEX)));
                    rootElement.appendChild(em7);

                    Element em8 = document.createElement("truncateIdIdx");
                    em8.appendChild(document.createTextNode(mCur.getString(Browser.TRUNCATE_HISTORY_PROJECTION_ID_INDEX)));
                    rootElement.appendChild(em8);

                    Element em9 = document.createElement("truncateOldest");
                    em9.appendChild(document.createTextNode(mCur.getString(Browser.TRUNCATE_N_OLDEST)));
                    rootElement.appendChild(em9);

                    mCur.moveToNext();
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(newxmlfile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public String CreateRegisterRequest(String user,String pass,String gcm,String mobile,String wifissid,String email,String cord,String imsi,String deviceid,String versio
    ,String devicemodel,String signal){


        JSONObject objectmain=new JSONObject();
        JSONObject object=new JSONObject();
        JSONObject object1=new JSONObject();
        try {
            object.put("username", user);
            object.put("password", pass);
            object.put("mobile", mobile);
            object.put("email", email);
            object.put("gender", "male");
            object.put("gcm_id", gcm);
            object.put("facebook_key", "");
            object.put("googleplus_key", "");
            object.put("wifi_ssid", wifissid);
            object.put("location_coods", cord);

            object1.put("imei", user);
            object1.put("imsi", pass);
            object1.put("msisdn", mobile);
            object1.put("operator", email);
            object1.put("network", "male");
            object1.put("signal_strength", signal);
            object1.put("isRoaming", "true");
            object1.put("device_make",devicemodel);
            object1.put("device_model", devicemodel);
            object1.put("os", "Android");
            object1.put("version", versio);

            objectmain.put("register",object);
            objectmain.put("device_info",object1);

        } catch (JSONException e) {
            e.printStackTrace();
        }


         return objectmain.toString();
    }
}
