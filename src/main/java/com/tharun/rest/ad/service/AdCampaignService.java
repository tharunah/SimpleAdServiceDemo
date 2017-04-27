package com.tharun.rest.ad.service;

import com.tharun.rest.ad.model.AdCampaign;

/**
 * Created by tharu on 4/27/2017.
 */
public interface AdCampaignService {
    void saveAdCampaign(AdCampaign adCampaign);
    AdCampaign fidByPartnerId(String partnerId);
    void updateAdCampaign(AdCampaign adCampaign) throws Exception;
    void deleteAdCampaign(String partnerId);
}
