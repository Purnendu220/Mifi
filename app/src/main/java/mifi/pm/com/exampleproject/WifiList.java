package mifi.pm.com.exampleproject;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mifi.pm.com.exampleproject.handler.SimpleDividerItemDecoration;
import mifi.pm.com.exampleproject.interfaces.VerticalChatAdapter;

public class WifiList extends AppCompatActivity {
    private RecyclerView mHorizontalRecycler_view, mVerticalRecycler_view;
    private VerticalChatAdapter mVerticalChatAdapter;
    private ArrayList<String> listWifi;
    int size = 0;
    List<ScanResult> results;
    WifiManager wifi;
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);
        mVerticalRecycler_view = (RecyclerView) findViewById(R.id.vertical_recycler_view);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        listWifi = new ArrayList<>();
        wifi.startScan();
        registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(WifiList.this,
                                "Wifi Scan",
                                "Wait .... ");
                        progressDialog.show();
                    }
                });
                results = wifi.getScanResults();
                size = results.size();
                scanWifi();
                Log.e("Tag","Size"+results.size());

            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        setupVerticalRecyclerView();
    }

    private void setupVerticalRecyclerView() {


    }
    private void scanWifi(){
        arraylist.clear();
        listWifi.clear();
       // wifi.startScan();

        Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
        try
        {
            size = size - 1;
            while (size >= 0)
            {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities);
                listWifi.add(results.get(size).SSID);
                arraylist.add(item);
                String capabilities= results.get(size).capabilities;
                Log.w ("TAG", results.get(size).SSID + " capabilities : " + capabilities);
                size--;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
                mVerticalRecycler_view.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
                mVerticalRecycler_view.setLayoutManager(layoutManager);
                mVerticalChatAdapter = new VerticalChatAdapter(getApplicationContext(), listWifi,wifi);
                mVerticalRecycler_view.setAdapter(mVerticalChatAdapter);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
