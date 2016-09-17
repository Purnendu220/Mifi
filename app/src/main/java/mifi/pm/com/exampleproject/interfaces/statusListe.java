package mifi.pm.com.exampleproject.interfaces;

/**
 * Created by Purnendu Mishra on 9/8/2016.
 */
public class statusListe {
    StatusLite wifiListe;

    public void setWifiListe(StatusLite wifiListe) {
        this.wifiListe = wifiListe;
    }

    public void OnStatusConnected(String sessionStart,String sessionDuration,String sessionUsage){
        if(wifiListe!=null)
        wifiListe.StatusConnected(sessionStart,sessionDuration,sessionUsage);
    }
    public void OnWifiDisCoonnected(String msg){
        wifiListe.WifiDisConnect(msg);
    }
    public interface StatusLite {
        void StatusConnected(String sessionStart,String sessionDuration,String sessionUsage);
        void WifiDisConnect(String msg);
    }
}
