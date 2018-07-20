package com.jiangwork.action.petstore.model;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiangwork.action.petstore.model.Deal.DealStatus;


public class DealStatusConverter implements AttributeConverter<DealStatus, Integer>{

    private static final Logger LOG = LoggerFactory.getLogger(DealStatusConverter.class);
    
    @Override
    public Integer convertToDatabaseColumn(DealStatus attribute) {
        // TODO Auto-generated method stub
        return attribute.getValue();
    }

    @Override
    public DealStatus convertToEntityAttribute(Integer dbData) {
        // TODO Auto-generated method stub
        for(DealStatus status: DealStatus.values()) {
            if(status.getValue() == dbData) {
                return status;
            }
        }
        String errorMsg = String.format("cannot map db value %s to OfferStatus enum.", dbData);
        LOG.error(errorMsg);
        throw new IllegalArgumentException(errorMsg);
    }

}
