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

/**
 * Integration tests for Ad Campaign REST service.
 * Uses Spring test framework with MockMvc.
 *
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class, WebMvcConfig.class, ClientConfig.class})
@WebAppConfiguration
public class SpringRestTestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/SimpleAdService";

    @Autowired
    private RestTemplate restTemplate;

    private HttpEntity<String> entity;

    @Before
    public void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(headers);
    }

    @Test
    public void testFindAdCampaignById() {
        // First create a campaign
        AdCampaign campaign = new AdCampaign("test-partner-1", 3600, "Test Ad Content");
        restTemplate.postForLocation(REST_SERVICE_URI + "/ad", campaign, AdCampaign.class);

        // Then retrieve it
        ResponseEntity<AdCampaign> response = restTemplate.exchange(
            REST_SERVICE_URI + "/ad/test-partner-1",
            HttpMethod.GET,
            entity,
            AdCampaign.class
        );

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("test-partner-1", response.getBody().getPartnerId());
    }

    @Test
    public void testCreateAdCampaign() {
        AdCampaign adCampaign = new AdCampaign("partner-123", 7200, "Campaign Ad Content");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/ad", adCampaign, AdCampaign.class);

        Assert.assertNotNull(uri);

        // Verify the campaign was created
        ResponseEntity<AdCampaign> response = restTemplate.exchange(
            REST_SERVICE_URI + "/ad/partner-123",
            HttpMethod.GET,
            entity,
            AdCampaign.class
        );

        AdCampaign retrievedCampaign = response.getBody();
        Assert.assertNotNull(retrievedCampaign);
        Assert.assertEquals(adCampaign.getPartnerId(), retrievedCampaign.getPartnerId());
        Assert.assertEquals(adCampaign.getAdContent(), retrievedCampaign.getAdContent());
    }
}
