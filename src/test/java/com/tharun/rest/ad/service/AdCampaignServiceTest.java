package com.tharun.rest.ad.service;

import com.tharun.rest.ad.model.AdCampaign;
import com.tharun.rest.ad.service.impl.AdCampaignServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Unit tests for AdCampaignService.
 * Tests business logic without requiring a running server.
 */
public class AdCampaignServiceTest {

    private AdCampaignService adCampaignService;

    @Before
    public void setUp() {
        adCampaignService = new AdCampaignServiceImpl();
    }

    @Test
    public void testSaveAdCampaign() {
        AdCampaign campaign = new AdCampaign("partner-1", 3600, "Test Ad");
        adCampaignService.saveAdCampaign(campaign);

        // Verify campaign was saved
        AdCampaign retrieved = adCampaignService.findByPartnerId("partner-1");
        assertNotNull(retrieved);
        assertEquals("partner-1", retrieved.getPartnerId());
    }

    @Test
    public void testFindByPartnerId() {
        AdCampaign campaign = new AdCampaign("partner-2", 7200, "Ad Content");
        adCampaignService.saveAdCampaign(campaign);

        AdCampaign found = adCampaignService.findByPartnerId("partner-2");
        assertNotNull(found);
        assertEquals("Ad Content", found.getAdContent());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindByPartnerIdNotFound() {
        adCampaignService.findByPartnerId("non-existent-partner");
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindByPartnerIdExpired() throws InterruptedException {
        // Create a campaign with 1 millisecond duration
        AdCampaign campaign = new AdCampaign("partner-3", 0, "Quick Ad");
        adCampaignService.saveAdCampaign(campaign);

        // Wait to ensure expiration
        Thread.sleep(100);

        // Should throw NoSuchElementException as campaign is expired
        adCampaignService.findByPartnerId("partner-3");
    }

    @Test
    public void testDeleteAdCampaign() {
        AdCampaign campaign = new AdCampaign("partner-4", 3600, "Ad to Delete");
        adCampaignService.saveAdCampaign(campaign);

        // Verify it exists
        AdCampaign found = adCampaignService.findByPartnerId("partner-4");
        assertNotNull(found);

        // Delete it
        adCampaignService.deleteAdCampaign("partner-4");

        // Should throw exception as it's deleted
        try {
            adCampaignService.findByPartnerId("partner-4");
            fail("Should have thrown NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testUpdateAdCampaign() throws Exception {
        AdCampaign campaign = new AdCampaign("partner-5", 3600, "Original Content");
        adCampaignService.saveAdCampaign(campaign);

        AdCampaign updated = new AdCampaign("partner-5", 7200, "Updated Content");
        adCampaignService.updateAdCampaign(updated);

        AdCampaign found = adCampaignService.findByPartnerId("partner-5");
        assertEquals(7200, found.getDuration());
        assertEquals("Updated Content", found.getAdContent());
    }

    @Test(expected = NoSuchElementException.class)
    public void testUpdateNonExistentCampaign() throws Exception {
        AdCampaign campaign = new AdCampaign("non-existent", 3600, "Content");
        adCampaignService.updateAdCampaign(campaign);
    }

    @Test
    public void testOneActiveCampaignPerPartner() {
        AdCampaign campaign1 = new AdCampaign("partner-6", 3600, "First Ad");
        adCampaignService.saveAdCampaign(campaign1);

        // Save another campaign for the same partner
        AdCampaign campaign2 = new AdCampaign("partner-6", 7200, "Second Ad");
        adCampaignService.saveAdCampaign(campaign2);

        // Should retrieve the second (latest) campaign
        AdCampaign found = adCampaignService.findByPartnerId("partner-6");
        assertEquals("Second Ad", found.getAdContent());
        assertEquals(7200, found.getDuration());
    }
}

