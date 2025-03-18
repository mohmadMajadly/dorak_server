package com.saker.dorak.Service;

import com.saker.dorak.Entity.DTO.OTP_DTO;
import com.saker.dorak.Entity.DTO.VerificationResponse;
import com.saker.dorak.Entity.business;
import com.saker.dorak.Entity.jwt;
import com.saker.dorak.Entity.session;
import com.saker.dorak.Entity.users;
import com.saker.dorak.Repository.usersRepository;
import com.saker.dorak.Service.SubService.businessService;
import com.saker.dorak.Service.SubService.sessionService;
import com.saker.dorak.Service.SubService.smsService;
import com.saker.dorak.Service.SubService.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {

    private final usersRepository usersRepository;
    private final sessionService sessionService;
    private final tokenService tokenService;
    private final businessService businessService;
    private final smsService smsService;

    public business addBusinessToUser(String token, String name, String description) {
        if (!tokenService.validateAccessToken(token)){
            return null;
        }
        Long user_id = tokenService.findUser_idByToken(token);
        Optional<users> userCheck = usersRepository.findById(user_id);
        if (userCheck.isEmpty()) {
            return null;
        }
        users user = userCheck.get();
        business business = new business();
        business.setName(name);
        business.setDescription(description);
        business.setUser(user);
        return businessService.saveBusiness(business);
    }

    public jwt createAccount(users users, String device_type, String ip) {
        try {
            users user = saveUser(users);
            session session = new session();
            session.setUser(user);
            session.setDevice_type(device_type);
            session.setIp(ip);
            jwt jwt = new jwt();
            jwt.setToken(tokenService.generateAccessToken(user.getId()));
            jwt.setRefresh_token(tokenService.generateRefreshToken(user.getId()));
            jwt.setUser_id(user.getId());
            session.setJwt(jwt);
            sessionService.saveSession(session);
            return jwt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateCredentials(String username, String password) {
        try {
            return usersRepository.checkPassword(username,password);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkPhoneExists(String username) {
        try {
            return usersRepository.isPhoneAvailableForUsername(username);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkUsernameExistence(String username) {
        try {
            return usersRepository.existsByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public VerificationResponse checkUserConditions(String username) {
        VerificationResponse response = new VerificationResponse();
        response.setUserExist(true);
        response.setCredentialsValid(true);
        response.setOnboardingComplete(true);
        response.setProfileComplete(checkPhoneExists(username));
        return response;
    }

    public OTP_DTO sendVerificationCode(Long user_id, String phoneNumber, String countryCode){
        try {
            if (!usersRepository.existsById(user_id)) {
                return new OTP_DTO("user not found");
            }
            users user = usersRepository.findById(user_id).get();
            user.generateCode();
            usersRepository.save(user);
            String fullPhoneNumber = countryCode + phoneNumber;
            smsService.sendSmsCode(fullPhoneNumber);
            OTP_DTO otpDTO = new OTP_DTO();
            otpDTO.setVerificationId(""+user_id);
            otpDTO.setPhoneNumber(phoneNumber);
            otpDTO.setCountryCode(countryCode);
            otpDTO.setMessage("success");
            return otpDTO;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public OTP_DTO VerificationCode(Long user_id, String phoneNumber, String countryCode, String code) {
        try {
            if (!usersRepository.existsById(user_id)) {
                return new OTP_DTO("user not found");
            }
            users user = usersRepository.findById(user_id).get();
            String fullPhoneNumber = countryCode + phoneNumber;
            if (!smsService.checkSmsCode(fullPhoneNumber, code)) {
                throw new RuntimeException();
            }
            user.setPhone(fullPhoneNumber);
            usersRepository.save(user);
            OTP_DTO otpDTO = new OTP_DTO();
            otpDTO.setVerificationId(""+user_id);
            otpDTO.setPhoneNumber(phoneNumber);
            otpDTO.setCountryCode(countryCode);
            otpDTO.setMessage("success");
            return otpDTO;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Long generateCodeToResetPasswordByUsername(String username) {
        users user = usersRepository.findByUsername(username);
        if (user == null) {
            return ((long) -1);
        }
        user.generateCode();
        usersRepository.save(user);
        return user.getId();
    }

    public Long generateCodeToResetPasswordByEmail(String email) {
        users user = usersRepository.findByEmail(email);
        if (user == null) {
            return ((long) -1);
        }
        user.generateCode();
        usersRepository.save(user);
        return user.getId();
    }

    public Long generateCodeToResetPasswordByPhone(String phone) {
        users user = usersRepository.findByPhone(phone);
        if (user == null) {
            return ((long) -1);
        }
        user.generateCode();
        usersRepository.save(user);
        return user.getId();
    }

    private String checkCodeToResetPassword(String code, Long user_id) {
        Optional<users> userCheck = usersRepository.findById(user_id);
        if (userCheck.isEmpty()) {
            return null;
        }
        users user = userCheck.get();
        String token = tokenService.generateAccessToken(Long.valueOf(user.getCode()));
        user.setForget_password_token(token);
        if (user.getNumber_of_enter_code() >= 3) {
            user.generateCode();
            user.setNumber_of_enter_code(0);
            token = null;
        }
        else if (user.getCode().equals(code)) {
            user.setNumber_of_enter_code(user.getNumber_of_enter_code() + 1);
        }
        usersRepository.save(user);
        return token;
    }

    //while progress
    public void resetPassword(String token, String password) {

    }

    private users saveUser(users user) {
        return usersRepository.save(user);
    }

    @Autowired
    public userService(usersRepository usersRepository, sessionService sessionService, tokenService tokenService, businessService businessService, smsService smsService) {
        this.usersRepository = usersRepository;
        this.sessionService = sessionService;
        this.tokenService = tokenService;
        this.businessService = businessService;
        this.smsService = smsService;
    }
}
