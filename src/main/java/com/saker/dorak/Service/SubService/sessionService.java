package com.saker.dorak.Service.SubService;


import com.saker.dorak.Entity.*;
import com.saker.dorak.Repository.sessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sessionService {
    private final sessionRepository sessionRepository;

    public void saveSession(session session) {
        sessionRepository.save(session);
    }

    @Autowired
    public sessionService(sessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}
