package com.jiangwork.action.petstore.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jiangwork.action.petstore.dao.OfferRepository;
import com.jiangwork.action.petstore.dao.UserRepository;
import com.jiangwork.action.petstore.model.Offer;
import com.jiangwork.action.petstore.rest.security.PetStoreUserDetails;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    public long addOffer(Offer offer) {
        if(offer.getTotal() <= 0) {
            throw new IllegalArgumentException(" offer total is less than 0: " + offer.getTotal());
        }
        offer.setPlaceTimestamp(System.currentTimeMillis());
        offer.setRest(offer.getTotal());
        PetStoreUserDetails userDetails = (PetStoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        offer.setUserId(userDetails.getUser().getId());
        return offerRepository.save(offer).getId();
    }
    
    
    public Offer getOffer(long id) {
        return offerRepository.findById(id).orElse(null);
    }
}
