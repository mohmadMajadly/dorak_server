package com.saker.dorak.Entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationResponse {
    private boolean isUserExist;
    private boolean isCredentialsValid;
    private boolean isOnboardingComplete;
    private boolean isProfileComplete;

    public VerificationResponse() {
    }

    public VerificationResponse(boolean isCredentialsValid, boolean isOnboardingComplete, boolean isProfileComplete, boolean isUserExist) {
        this.isUserExist = isUserExist;
        this.isCredentialsValid = isCredentialsValid;
        this.isOnboardingComplete = isOnboardingComplete;
        this.isProfileComplete = isProfileComplete;
    }
}

