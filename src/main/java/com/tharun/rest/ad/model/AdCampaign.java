package com.tharun.rest.ad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * Ad Campaign model
 * Represents an advertising campaign with partner ID, duration, and content.
 *
 * @since 1.0
 */
public class AdCampaign {

    @JsonProperty
    private String partnerId;

    @JsonProperty
    private int duration;

    @JsonProperty
    private String adContent;

    @JsonProperty
    private long timestamp;

    public AdCampaign() {
        this.timestamp = Instant.now().toEpochMilli();
    }

    public AdCampaign(String partnerId, int duration, String adContent) {
        this.partnerId = partnerId;
        this.duration = duration;
        this.adContent = adContent;
        this.timestamp = Instant.now().toEpochMilli();
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

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "AdCampaign{" +
                "partnerId='" + partnerId + '\'' +
                ", duration=" + duration +
                ", adContent='" + adContent + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdCampaign that = (AdCampaign) o;

        if (duration != that.duration) return false;
        if (timestamp != that.timestamp) return false;
        if (!partnerId.equals(that.partnerId)) return false;
        return adContent.equals(that.adContent);
    }

    @Override
    public int hashCode() {
        int result = partnerId.hashCode();
        result = 31 * result + duration;
        result = 31 * result + adContent.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
