
package mifi.pm.com.exampleproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisement {

    @SerializedName("delete-after-seconds")
    @Expose
    private String deleteAfterSeconds;
    @SerializedName("display-adhoc")
    @Expose
    private String displayAdhoc;
    @SerializedName("on-device-event")
    @Expose
    private String onDeviceEvent;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("schedule-timestamp")
    @Expose
    private String scheduleTimestamp;
    @SerializedName("repeat-frequency")
    @Expose
    private String repeatFrequency;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ad-url")
    @Expose
    private String adUrl;
    @SerializedName("view-points")
    @Expose
    private String viewPoints;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The deleteAfterSeconds
     */
    public String getDeleteAfterSeconds() {
        return deleteAfterSeconds;
    }

    /**
     * 
     * @param deleteAfterSeconds
     *     The delete-after-seconds
     */
    public void setDeleteAfterSeconds(String deleteAfterSeconds) {
        this.deleteAfterSeconds = deleteAfterSeconds;
    }

    /**
     * 
     * @return
     *     The displayAdhoc
     */
    public String getDisplayAdhoc() {
        return displayAdhoc;
    }

    /**
     * 
     * @param displayAdhoc
     *     The display-adhoc
     */
    public void setDisplayAdhoc(String displayAdhoc) {
        this.displayAdhoc = displayAdhoc;
    }

    /**
     * 
     * @return
     *     The onDeviceEvent
     */
    public String getOnDeviceEvent() {
        return onDeviceEvent;
    }

    /**
     * 
     * @param onDeviceEvent
     *     The on-device-event
     */
    public void setOnDeviceEvent(String onDeviceEvent) {
        this.onDeviceEvent = onDeviceEvent;
    }

    /**
     * 
     * @return
     *     The schedule
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * 
     * @param schedule
     *     The schedule
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The scheduleTimestamp
     */
    public String getScheduleTimestamp() {
        return scheduleTimestamp;
    }

    /**
     * 
     * @param scheduleTimestamp
     *     The schedule-timestamp
     */
    public void setScheduleTimestamp(String scheduleTimestamp) {
        this.scheduleTimestamp = scheduleTimestamp;
    }

    /**
     * 
     * @return
     *     The repeatFrequency
     */
    public String getRepeatFrequency() {
        return repeatFrequency;
    }

    /**
     * 
     * @param repeatFrequency
     *     The repeat-frequency
     */
    public void setRepeatFrequency(String repeatFrequency) {
        this.repeatFrequency = repeatFrequency;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The adUrl
     */
    public String getAdUrl() {
        return adUrl;
    }

    /**
     * 
     * @param adUrl
     *     The ad-url
     */
    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    /**
     * 
     * @return
     *     The viewPoints
     */
    public String getViewPoints() {
        return viewPoints;
    }

    /**
     * 
     * @param viewPoints
     *     The view-points
     */
    public void setViewPoints(String viewPoints) {
        this.viewPoints = viewPoints;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
