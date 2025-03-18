package com.saker.dorak.Service;

import com.saker.dorak.Repository.*;
import com.saker.dorak.Entity.*;
import com.saker.dorak.Service.SubService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class customerService {

    private final customerRepository customerRepository;
    private final sessionService sessionService;
    private final tokenService tokenService;

    public void createAccount(String phone, String device_type, String ip) {
        customer customer = saveCustomer(phone);
        session session = new session();
        session.setCustomer(customer);
        session.setDevice_type(device_type);
        session.setIp(ip);
        jwt jwt = new jwt();
        jwt.setToken(tokenService.generateAccessToken(customer.getId()));
        jwt.setRefresh_token(tokenService.generateRefreshToken(customer.getId()));
        session.setJwt(jwt);
        sessionService.saveSession(session);
    }

    private customer saveCustomer(String phone) {
        customer customer = new customer();
        customer.setPhone(phone);
        return customerRepository.save(customer);
    }

    @Autowired
    public customerService(customerRepository customerRepository, sessionService sessionService, tokenService tokenService) {
        this.customerRepository = customerRepository;
        this.sessionService = sessionService;
        this.tokenService = tokenService;
    }
}
