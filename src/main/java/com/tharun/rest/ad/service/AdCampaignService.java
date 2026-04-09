package com.tharun.rest.ad.service;

import com.tharun.rest.ad.model.AdCampaign;

/**
 * Service interface for Ad Campaign management.
 *
 * @since 1.0
 */
public interface AdCampaignService {

    /**
     * Save a new ad campaign or replace an existing one for a partner.
     *
     * @param adCampaign the campaign to save
     */
    void saveAdCampaign(AdCampaign adCampaign);

    /**
     * Find an active ad campaign by partner ID.
     *
     * @param partnerId the partner identifier
     * @return the active ad campaign
     * @throws java.util.NoSuchElementException if campaign doesn't exist or has expired
     */
    AdCampaign findByPartnerId(String partnerId);

    /**
     * Update an existing ad campaign.
     *
     * @param adCampaign the campaign to update
     * @throws Exception if campaign doesn't exist
     */
    void updateAdCampaign(AdCampaign adCampaign) throws Exception;

    /**
     * Delete an ad campaign for a partner.
     *
     * @param partnerId the partner identifier
     */
    void deleteAdCampaign(String partnerId);
}
