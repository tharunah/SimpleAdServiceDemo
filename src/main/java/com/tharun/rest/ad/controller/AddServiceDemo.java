package com.tharun.rest.ad.controller;

import com.tharun.rest.ad.model.AdCampaign;
import com.tharun.rest.ad.service.AdCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tharu on 4/27/2017.
 */
@RestController
public class AddServiceDemo {

    @Autowired
    AdCampaignService adCampaignService;

    @RequestMapping(method = RequestMethod.POST, value = "/ad")
    public AdCampaign createAdCampaign(@RequestBody final AdCampaign adCampaign) {
        adCampaignService.saveAdCampaign(adCampaign);
        return adCampaign;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ad/{partnerId}")
    public AdCampaign getAdCampaign(@PathVariable("partnerId") String partnerId) {
        return adCampaignService.fidByPartnerId(partnerId);
    }
}
