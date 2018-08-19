package com.jiangwork.action.petstore.rest.security.acl;

import org.springframework.security.acls.model.ObjectIdentity;

import java.io.Serializable;

/**
 * Created by Jiang on 2018/8/19.
 */
public interface PetStoreIdentity extends ObjectIdentity, Serializable {
}
