package mifi.pm.com.exampleproject.interfaces;

/**
 * Created by Purnendu Mishra on 8/13/2016.
 */
public class SmsHandler {
Smsliste smsliste;

    public Smsliste getSmsliste() {
        return smsliste;
    }
    public void OnsmsReceived(String sms){
        smsliste.smsReceived(sms);

    }
    public void setSmsliste(Smsliste smsliste) {
        this.smsliste = smsliste;
    }

    public interface Smsliste {
        void smsReceived(String sms);
    }


}
