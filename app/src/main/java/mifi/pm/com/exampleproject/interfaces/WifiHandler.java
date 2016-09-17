package mifi.pm.com.exampleproject.interfaces;

/**
 * Created by Purnendu Mishra on 9/5/2016.
 */
public class WifiHandler{
WifiListe wifiListe;

    public void setWifiListe(WifiListe wifiListe) {
        this.wifiListe = wifiListe;
    }

    public void OnWifiCoonnected(String msg){
        wifiListe.WifiConnected(msg);
    }
    public void OnWifiDisCoonnected(String msg){
        wifiListe.WifiDisConnect(msg);
    }
    public interface WifiListe {
    void WifiConnected(String msg);
    void WifiDisConnect(String msg);
    }
}
