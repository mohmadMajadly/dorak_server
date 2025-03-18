package com.saker.dorak.Entity.DTO;


import com.saker.dorak.Entity.users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userRegisterDTO {
    private String fullName;
    private String username;
    private String password;
    private String emailAddress;
    private String dateOfBirth;
    private String gender;
    private String hasAcceptedTerms;
    private String device_type;

    public userRegisterDTO() {
    }
    public users convertToUser(){
        users user = new users();
        user.setName(this.fullName);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.emailAddress);
        user.setBirthday(this.dateOfBirth);
        user.setActive(false);
        if (this.gender.equals("male")) {
            user.setGender(true);
        }
        else {
            user.setGender(false);
        }
        return user;
    }
}
