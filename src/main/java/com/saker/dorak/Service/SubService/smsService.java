package com.saker.dorak.Service.SubService;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class smsService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.verify.sid}")
    private String verifyServiceSid;

    public void sendSmsCode(String phoneNumber) {
        try {
            Twilio.init(accountSid, authToken);
            Verification verification = Verification.creator(
                    verifyServiceSid,
                    phoneNumber,
                    "sms"
            ).create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkSmsCode(String phoneNumber, String code) {
        try {
            Twilio.init(accountSid, authToken);
            VerificationCheck verificationCheck = VerificationCheck.creator(
                    verifyServiceSid)
                    .setTo(phoneNumber)
                    .setCode(code)
                    .create();
            System.out.println(verificationCheck.getStatus());
            return verificationCheck.getStatus().equals("approved");
        } catch (Exception e) {
            return false;
        }
    }

    public smsService() {

    }
}
