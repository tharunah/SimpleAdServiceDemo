package com.tharun.rest.ad.controller;

import com.tharun.rest.ad.model.AdCampaign;
import com.tharun.rest.ad.service.AdCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Ad Campaign operations.
 * Handles HTTP requests for creating and retrieving ad campaigns.
 *
 * @since 1.0
 */
@RestController
public class AddServiceDemo {

    @Autowired
    private AdCampaignService adCampaignService;

    /**
     * Create a new ad campaign via HTTP POST.
     *
     * @param adCampaign the campaign data
     * @return the created campaign
     */
    @PostMapping("/ad")
    public AdCampaign createAdCampaign(@RequestBody final AdCampaign adCampaign) {
        adCampaignService.saveAdCampaign(adCampaign);
        return adCampaign;
    }

    /**
     * Get an active ad campaign for a partner via HTTP GET.
     *
     * @param partnerId the partner identifier
     * @return the active campaign
     * @throws java.util.NoSuchElementException if no active campaign exists
     */
    @GetMapping("/ad/{partnerId}")
    public AdCampaign getAdCampaign(@PathVariable("partnerId") String partnerId) {
        return adCampaignService.findByPartnerId(partnerId);
    }
}
