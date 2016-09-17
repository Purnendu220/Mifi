package mifi.pm.com.exampleproject.interfaces;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mifi.pm.com.exampleproject.R;
import mifi.pm.com.exampleproject.handler.AlertUtils;
import mifi.pm.com.exampleproject.handler.AppSharedPreferences;

/**
 * Created by Mobikasa on 8/26/2016.
 */
public class VerticalChatAdapter extends RecyclerView.Adapter<VerticalChatAdapter.VerticalChatHolder> {
    ArrayList<String> WifiList = new ArrayList<>();
    private Context mContext;
    WifiManager wifi;

    public VerticalChatAdapter(Context context, ArrayList<String> WifiList,WifiManager wifi) {
        this.WifiList.addAll(WifiList);
        this.mContext = context;
        this.wifi =wifi;
    }

    @Override
    public VerticalChatHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wifi_list_layout, null);
        VerticalChatHolder mh = new VerticalChatHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(VerticalChatHolder holder, int position) {
       /* refrenceWrapper.getmPictureUtilHandler().setPictureFromPicasso(mContext, mChatApiList.get(position).getImagePath(), holder.mCircleView, 0);
        holder.mTxt_Chat_message.setText(mMessageList.get(position).getBody());
        holder.mTxt_Date_Chat.setText(mMessageList.get(position).getDate());
        if (mMessageList.get(position).getReadstatus().equalsIgnoreCase(GlobalConstants.ReadStatus.READ)) {
            holder.mText_dot.setVisibility(View.GONE);
        }*/
        holder.mTxt_Wifi.setText(WifiList.get(position));
        Log.e("Tag","Size"+WifiList.get(position));

    }

    @Override
    public int getItemCount() {
        return (null != WifiList ? WifiList.size() : 0);
    }

    public class VerticalChatHolder extends RecyclerView.ViewHolder {
        protected TextView mTxt_Wifi, mTxt_wifi_Status_name, mTxt_Date_Wifi;
        protected CircleImageView mCircleView;

        public VerticalChatHolder(View view) {
            super(view);
            this.mTxt_wifi_Status_name = (TextView) view.findViewById(R.id.txt_message_chat);
            this.mCircleView = (CircleImageView) view.findViewById(R.id.circleView);
            this.mTxt_Wifi = (TextView) view.findViewById(R.id.txt_name_chat);
            this.mTxt_Date_Wifi = (TextView) view.findViewById(R.id.txt_date_chat);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertUtils.showToast(mContext,WifiList.get(getAdapterPosition()));
                    connectToWifi(WifiList.get(getAdapterPosition()),"bynq61hm");
                }
            });
        }
    }
    public void connectToWifi(String ssid,String networkPass ) {

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssid + "\""; // Please note the quotes.
        // String should contain
        // ssid in quotes

       /* conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        conf.preSharedKey = "\""+ networkPass +"\"";*/
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        wifi.addNetwork(conf);
        Log.d("MIFI", ssid+" added");

        List<WifiConfiguration> list = wifi.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                wifi.disconnect();
                wifi.enableNetwork(i.networkId, true);
                wifi.reconnect();
                Log.d("MIFI", "conneting to: ssid"+i.SSID);
                break;
            }
        }
    }
}