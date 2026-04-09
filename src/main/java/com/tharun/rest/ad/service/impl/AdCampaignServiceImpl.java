package com.tharun.rest.ad.service.impl;

import com.tharun.rest.ad.model.AdCampaign;
import com.tharun.rest.ad.service.AdCampaignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Service implementation for Ad Campaign management.
 * Handles storage and retrieval of ad campaigns with expiration logic.
 *
 * @since 1.0
 */
@Service("AdCampaignService")
@Transactional
public class AdCampaignServiceImpl implements AdCampaignService {

    private final HashMap<String, AdCampaign> adCampaignMap = new HashMap<>(100);

    @Override
    public void saveAdCampaign(AdCampaign adCampaign) {
        if (adCampaign != null) {
            adCampaignMap.put(adCampaign.getPartnerId(), adCampaign);
        }
    }

    @Override
    public AdCampaign findByPartnerId(String partnerId) {
        Objects.requireNonNull(partnerId, "Partner ID cannot be null");

        AdCampaign adCampaign = adCampaignMap.get(partnerId);
        if (adCampaign == null) {
            throw new NoSuchElementException("Ad campaign not found for partner: " + partnerId);
        }

        // Check if campaign has expired
        long expireTime = adCampaign.getTimestamp() + (long) adCampaign.getDuration() * 1000;
        if (expireTime <= System.currentTimeMillis()) {
            throw new NoSuchElementException("Ad campaign has expired for partner: " + partnerId);
        }

        return adCampaign;
    }

    @Override
    public void updateAdCampaign(AdCampaign adCampaign) throws Exception {
        Objects.requireNonNull(adCampaign, "Ad campaign cannot be null");

        if (adCampaignMap.containsKey(adCampaign.getPartnerId())) {
            adCampaignMap.put(adCampaign.getPartnerId(), adCampaign);
        } else {
            throw new NoSuchElementException("Ad is not available in system for partner: " + adCampaign.getPartnerId());
        }
    }

    @Override
    public void deleteAdCampaign(String partnerId) {
        Objects.requireNonNull(partnerId, "Partner ID cannot be null");
        adCampaignMap.remove(partnerId);
    }
}
