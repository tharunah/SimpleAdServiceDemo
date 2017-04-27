package com.tharun.rest.ad.service.impl;

import com.tharun.rest.ad.model.AdCampaign;
import com.tharun.rest.ad.service.AdCampaignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by tharu on 4/27/2017.
 */
@Service("AdCampaignService")
@Transactional
public class AdCampaignServiceImpl implements AdCampaignService {

    private HashMap<String, AdCampaign> adCampaignMap = new HashMap<>(100);

    @Override
    public void saveAdCampaign(AdCampaign adCampaign) {
        if (adCampaign != null) {
            adCampaignMap.put(adCampaign.getPartnerId(), adCampaign);
        }
    }

    @Override
    public AdCampaign fidByPartnerId(String partnerId) {
        if (adCampaignMap.containsKey(partnerId)) {
            AdCampaign adCampaign = adCampaignMap.get(partnerId);
            long expireTime = adCampaign.getTimestamp().getTime() + adCampaign.getDuration();
            if (expireTime > System.currentTimeMillis()) {
                return adCampaign;
            }
        }
        throw new NoSuchElementException("Ad campaign not found");

    }

    @Override
    public void updateAdCampaign(AdCampaign adCampaign) throws Exception {
        if (adCampaignMap.containsKey(adCampaign.getPartnerId())) {
            adCampaignMap.put(adCampaign.getPartnerId(), adCampaign);
        } else {
            throw new NoSuchElementException("Ad is not available in system - %s" + adCampaign.getPartnerId());
        }
    }

    @Override
    public void deleteAdCampaign(String partnerId) {
        adCampaignMap.remove(partnerId);
    }
}
