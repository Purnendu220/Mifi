
package mifi.pm.com.exampleproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("advertisement")
    @Expose
    private Advertisement advertisement;

    /**
     * 
     * @return
     *     The advertisement
     */
    public Advertisement getAdvertisement() {
        return advertisement;
    }

    /**
     * 
     * @param advertisement
     *     The advertisement
     */
    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

}
