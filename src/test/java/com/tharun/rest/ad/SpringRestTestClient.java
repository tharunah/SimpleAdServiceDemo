package com.tharun.rest.ad;

import com.tharun.rest.ad.config.ClientConfig;
import com.tharun.rest.ad.config.MainConfig;
import com.tharun.rest.ad.config.WebMvcConfig;
import com.tharun.rest.ad.model.AdCampaign;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by tharu on 4/27/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class, WebMvcConfig.class, ClientConfig.class})
@WebAppConfiguration
public class SpringRestTestClient {
    public static final String REST_SERVICE_URI = "http://localhost:8080/SimpleAdService";

    @Autowired
    RestTemplate restTemplate;

    HttpEntity<String> entity;

    @Before
    public void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>(headers);
    }

    @Test
    public void findAdCampaignbyIdTest() {
        ResponseEntity<AdCampaign> response = restTemplate.exchange(REST_SERVICE_URI + "/ad/12", HttpMethod.GET, entity, AdCampaign.class);
        AdCampaign adCampaign = response.getBody();
        Assert.assertEquals("12", adCampaign.getPartnerId());
    }

    @Test()
    public void findAdCampaignbyIdTest_FailCase() {
        ResponseEntity<AdCampaign> response = restTemplate.exchange(REST_SERVICE_URI + "/ad/12", HttpMethod.GET, entity, AdCampaign.class);
        Assert.assertFalse(response.getBody() instanceof AdCampaign);
    }

    @Test
    public void createAdCampignTest() {
        AdCampaign adCampaign = new AdCampaign("12", 2345, "test_ad_campaign");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/ad", adCampaign, AdCampaign.class);

        ResponseEntity<AdCampaign> response = restTemplate.exchange(REST_SERVICE_URI + "/ad/12", HttpMethod.GET, entity, AdCampaign.class);
        AdCampaign adCampaign1 = response.getBody();

        Assert.assertEquals(adCampaign.getPartnerId(), adCampaign1.getPartnerId());
    }
}
