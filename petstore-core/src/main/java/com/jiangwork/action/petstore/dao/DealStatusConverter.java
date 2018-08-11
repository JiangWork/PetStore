package com.jiangwork.action.petstore.dao;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiangwork.action.petstore.dao.DealDO.DealStatus;

import java.util.stream.Stream;


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
//        Stream.of(DealStatus.values()).filter((status)->status.getValue()==dbData)
//                .findFirst().orElseThrow(new IllegalArgumentException(errorMsg));
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
