package com.tharun.rest.ad;

import com.tharun.rest.ad.model.AdCampaign;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by tharu on 4/27/2017.
 */
public class PlainRestApplicationTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SimpleAdService";

    /* GET */
    private static void getAdCampign(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        AdCampaign adCampaign = restTemplate.getForObject(REST_SERVICE_URI+"/ad/1", AdCampaign.class);
        System.out.println(adCampaign);
    }

    /* POST */
    private static void createAdCampaign() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        AdCampaign adCampaign = new AdCampaign("123ed",127,"test ad for this campaign");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/ad", adCampaign, AdCampaign.class);
        System.out.println("Location : "+uri.toASCIIString());
    }


    public static void main(String[] args) {
        createAdCampaign();
        getAdCampign();
    }
}
