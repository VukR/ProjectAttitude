package com.projectattitude.projectattitude.Objects;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by Chris on 2/24/2017.
 * This is the mood class that represents the heart of this application. The majority of functions
 * here are getters and setters for the various properties of the mood object such as date and
 * trigger.
 */

public class Mood implements Serializable {

    private Date moodDate;
    private String emotionState;
    private GeoPoint geoLocation;
    private String trigger;
    private String socialSituation;

    @JestId
    private String id;
    private String photo;


    /**
     * Initiates the mood object.
     */
    public Mood() {
    }

    /**
     * Sets the moodDate to the given moodDate.
     * @param moodDate moodDate
     */
    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    /**
     * Returns the emotionState.
     * @return emotionState
     */
    public String getEmotionState() {
        return emotionState;
    }

    /**
     * Sets the emotionState to the given emotionState.
     * @param emotionState emotionState
     */
    public void setEmotionState(String emotionState) {
        this.emotionState = emotionState;
    }

    /**
     * Gets the geoLocation.
     * @return geoLocation
     */
    public GeoPoint getGeoLocation() {
        return geoLocation;
    }

    /**
     * Sets the geoLocation
     * @param geoLocation geoLocation
     */
    public void setGeoLocation(GeoPoint geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * Gets the trigger.
     * @return trigger
     */
    public String getTrigger() {
        return trigger;
    }

    /**
     * Sets the trigger.
     * @param trigger trigger
     */
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    /**
     * Gets the socialSituation.
     * @return socialSituation
     */
    public String getSocialSituation() {
        return socialSituation;
    }

    /**
     * Sets the socialSituation.
     * @param socialSituation socialSituation
     */
    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    /**
     * Gets the moodDate.
     * @return moodDate
     */
    public Object getMoodDate() {
        return moodDate;
    }

    /**
     * This is what the list view shows, we can edit this later
     * @return a string currently containing the emotionState
     * @updated on 3/6/17
     */
    @Override
    public String toString(){
        return(emotionState);
    }

    /**
     * Gets the userID
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the userID
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets photo variable as string of 64 bit encoded
     * @param photo photo
     */
    public void setPhoto(String photo){

        this.photo = photo;
    }

    /**
     * Returns photo as string
     * @return photo
     */
    public String getPhoto(){
        return photo;
    }
}
