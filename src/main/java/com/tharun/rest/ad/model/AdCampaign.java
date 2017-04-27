package com.tharun.rest.ad.model;

import java.sql.Timestamp;

/**
 * Created by tharu on 4/27/2017.
 */
public class AdCampaign {
    private String partnerId;
    private int duration;
    private String adContent;
    private Timestamp timestamp;

    public AdCampaign(){
      this.timestamp = new Timestamp(System.currentTimeMillis());
    }
    public AdCampaign(String partnerId, int duration, String adContent) {
        this.partnerId = partnerId;
        this.duration = duration;
        this.adContent = adContent;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getPartnerId() {
        return partnerId;
    }

    public int getDuration() {
        return duration;
    }

    public String getAdContent() {
        return adContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
