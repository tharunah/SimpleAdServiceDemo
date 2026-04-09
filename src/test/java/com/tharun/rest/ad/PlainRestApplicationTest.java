package com.tharun.rest.ad;

import com.tharun.rest.ad.model.AdCampaign;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Manual integration test for Ad Campaign REST service.
 * This is a standalone test that doesn't use Spring test framework.
 * Run this after starting the server with: mvn jetty:run
 *
 * @since 1.0
 */
public class PlainRestApplicationTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SimpleAdService";

    /**
     * GET request to retrieve an ad campaign
     */
    private static void getAdCampaign() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            AdCampaign adCampaign = restTemplate.getForObject(REST_SERVICE_URI + "/ad/partner-1", AdCampaign.class);
            System.out.println("Retrieved Campaign: " + adCampaign);
        } catch (Exception e) {
            System.err.println("Error retrieving campaign: " + e.getMessage());
        }
    }

    /**
     * POST request to create a new ad campaign
     */
    private static void createAdCampaign() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            AdCampaign adCampaign = new AdCampaign("partner-1", 3600, "Sample Ad Content");
            URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/ad", adCampaign, AdCampaign.class);
            System.out.println("Created Campaign Location: " + uri.toASCIIString());
        } catch (Exception e) {
            System.err.println("Error creating campaign: " + e.getMessage());
        }
    }

    /**
     * Main method to execute manual tests
     */
    public static void main(String[] args) {
        System.out.println("SimpleAdService Manual Integration Tests");
        System.out.println("========================================");

        System.out.println("\n1. Creating ad campaign...");
        createAdCampaign();

        System.out.println("\n2. Retrieving ad campaign...");
        getAdCampaign();
    }
}
