package mifi.pm.com.exampleproject;

import android.widget.ProgressBar;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project : Mobikasa Retrofit Lib
 * Author : Balwinder Singh Madaan
 * Creation Date : 25-july-2016
 */
public class Constants {
    static boolean isProduction = true;
    public static CircleImageView ivImage;
    public static int imageType;
    public static ProgressBar mImageUploadProgress;
    public static ProgressBar mImageUplaodProgressKid;
    public static boolean fromMapCardFragment;

    public interface WebConstants {
        String HOST_API = "http://cardamom-live.mobikasa.net/api/v1/";// Staging IP
        String DISPLAY_IMAGE = "http://cardamom-live.mobikasa.net";
        String GOOGLE_PLACE_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        String GOOGLE_AUTOCOMPLETE_API_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";
        //String HOST_API = "";
    }

    public interface ServiceConstants {
        String SIGNUP = "/users/signupemail";
        String SIGNIN = "/users/signinemail";
        String FACEBOOKSIGNUP = "/users/facebooksignup";
        String FORGOTPASSWORD = "/users/forgotpassword";
        String UPLOADIMAGE = "/uploadimage";
        String CHANGEPASSWORD="/users/changepassword";
        String CONTACTUS="/users/contactadmin";
        String SIGNOUT="/users/signoutuser";
        String MAPUSERDATA="/users/matchinguserprofile";
        String OTHERUSERDETAILS="/users/otheruserdetails";
        String SENDINGWAVE="/users/sendingwave";
        String FACEBOOKAPI="/users/getfbusers";
        String UPDATE_USER = "/users/updateuserprofile";
        String SEARCHSCHOOL = "/users/searchcolleges";
        String VIEWINTRODUCTION = "/users/viewintroduction";
        String SAVEDCARDPROFILE = "/users/getsavedprofiles";
        String NOTIFICATION = "/notifications";
        String GETCHATUSERS= "/users/getchatusers";
        String READDELETENOTIFICATION = "/readdeletenotification";
        String NOTIFICATIONCOUNT = "/notificationscount";
        String USERVOTECONCIERGE="/users/uservote";
        String BLOCKUSER="/users/blockuser";
        String REPORTUSER="/users/reportuser";
    }

    public interface GeneralConstants {
        String IMAGE_STORE = "Image Capture";
        int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
        int RESULT_LOAD_IMAGE = 101;
    }

    public interface GCM_CONSTANTS {
        // Google Project Number
        String GOOGLE_PROJECT_ID = "257696593707";
        String MESSAGE_KEY = "subtitle";
        String SUBJECT_KEY = "title";
        String REGISTER_AGAIN = "register_again";
        String FROM_NOTIFICATION = "from_notification";
    }

    public interface FacebookConstants {
        String FB_PIC_START_URL = "https://graph.facebook.com/";
        String FB_PIC_END_URL = "/picture?type=large";
        String PUBLICPROFILE = "public_profile";
        String EMAIL = "email";
        String USERFRIENDS = "user_friends";
        String FIELDS = "fields";
        String ID = "id";
        String NAME = "name";
        String LINK = "link";
        String PICTURE = "picture";
    }

    public interface TermsAndConditionsPrivacyPolicy {
        String TERMSANDCONDITIONS = "http://www.cardamomapp.com/#!terms-conditions/zcnpi";
        String PRIVACYPOLICY = "http://www.cardamomapp.com/#!privacy-policy/sbwvp";
    }
    public interface WaveUser{
        String WAVE="wave";
        String NOTHANKS="nothanks";
        String NOTNOW="showlater";
    }
    public interface className
    {
        String COMMONACTIVITY = "CommonActivity";
        String HOMEACTIVITY="HomeActivity";
    }
}



