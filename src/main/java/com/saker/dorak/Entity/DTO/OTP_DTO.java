package com.saker.dorak.Entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTP_DTO {
    private String verificationId;
    private String phoneNumber;
    private String countryCode;
    private String message;
    private String code;

    public OTP_DTO() {
    }

    public OTP_DTO(String message) {
        this.message = message;
    }
}
