package com.saker.dorak.Service.SubService;

import com.saker.dorak.Entity.business;
import com.saker.dorak.Repository.businessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class businessService {

    private final businessRepository businessRepository;

    public business saveBusiness(business business) {
        return businessRepository.save(business);
    }

    @Autowired
    public businessService(businessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }
}
