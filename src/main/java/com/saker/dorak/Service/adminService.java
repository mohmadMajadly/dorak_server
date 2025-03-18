package com.saker.dorak.Service;

import com.saker.dorak.Repository.*;
import com.saker.dorak.Entity.*;
import com.saker.dorak.Service.SubService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminService {

    private final adminRepository adminRepository;
    private final sessionService sessionService;
    private final tokenService tokenService;


    public void createAccount(String name, String email, String username, String password, String phone, String device_type, String ip) {
        admin admin = saveAdmin(name, email, username, password, phone);
        session session = new session();
        session.setAdmin(admin);
        session.setDevice_type(device_type);
        session.setIp(ip);
        jwt jwt = new jwt();
        jwt.setToken(tokenService.generateAccessToken(admin.getId()));
        jwt.setRefresh_token(tokenService.generateRefreshToken(admin.getId()));
        session.setJwt(jwt);
        sessionService.saveSession(session);
    }

    private admin saveAdmin(String name, String email, String username, String password, String phone) {
        admin admin = new admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setPhone(phone);
        return adminRepository.save(admin);
    }

    @Autowired
    public adminService(adminRepository adminRepository, sessionService sessionService, tokenService tokenService) {
        this.adminRepository = adminRepository;
        this.sessionService = sessionService;
        this.tokenService = tokenService;
    }
}
