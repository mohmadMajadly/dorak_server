package com.saker.dorak.Service.SubService;

import com.saker.dorak.Repository.jwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
public class tokenService {

    private final String SECRET_KEY = "test";
    private final String AES_KEY = "test123456789abc"; // 16-Byte key for AES
    private final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 60 * 24 * 15; // 15 دقيقة
    private final long REFRESH_TOKEN_EXPIRY = 1000 * 60 * 60 * 24 * 15; // 15 يوم
    @Autowired
    private jwtRepository jwtRepository;

    // إنشاء Access Token
    public String generateAccessToken(Long userId) {
        long expiryTime = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY;
        String data = userId + ":" + expiryTime;
        String token = generateToken(data);
        if (jwtRepository.existsByToken(token)) {
            return generateAccessToken(userId);
        }
        return token;
    }

    // إنشاء Refresh Token
    public String generateRefreshToken(Long userId) {
        long expiryTime = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY;
        String uniqueId = UUID.randomUUID().toString(); // رمز عشوائي لزيادة الأمان
        String data = userId + ":" + expiryTime + ":" + uniqueId;
        String refresh_token = generateToken(data);
        if (jwtRepository.existsByRefresh_token(refresh_token)) {
            return generateRefreshToken(userId);
        }
        return refresh_token;
    }

    // التحقق من Access Token
    public boolean validateAccessToken(String token) {
        return validateToken(token);
    }

    // التحقق من Refresh Token
    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

    // تجديد Access Token باستخدام Refresh Token
    public String renewAccessToken(String refreshToken, Long userId) {
        if (validateRefreshToken(refreshToken)) {
            return generateAccessToken(userId);
        }
        throw new RuntimeException("Invalid Refresh Token");
    }

    // توليد التوكن (مشفر وموقع)
    private String generateToken(String data) {
        try {
            // تشفير البيانات باستخدام AES
            String encryptedData = encryptData(data);

            // توقيع البيانات المشفرة باستخدام HMAC
            String signature = generateHmac(encryptedData);

            // إرجاع التوكن النهائي
            return encryptedData + ":" + signature;
        } catch (Exception e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    // التحقق من التوكن
    private boolean validateToken(String token) {
        try {
            String[] parts = token.split(":");
            if (parts.length != 2) {
                return false;
            }

            String encryptedData = parts[0];
            String providedSignature = parts[1];

            // التحقق من التوقيع
            String expectedSignature = generateHmac(encryptedData);
            if (!providedSignature.equals(expectedSignature)) {
                return false; // توقيع غير صالح
            }

            // فك التشفير والتحقق من وقت الانتهاء
            String data = decryptData(encryptedData);
            String[] dataParts = data.split(":");
            if (dataParts.length < 2) {
                return false;
            }

            long expiryTime = Long.parseLong(dataParts[1]);
            return System.currentTimeMillis() <= expiryTime; // انتهت صلاحية التوكن
        } catch (Exception e) {
            return false;
        }
    }

    // تشفير البيانات باستخدام AES
    private String encryptData(String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // فك تشفير البيانات باستخدام AES
    private String decryptData(String encryptedData) throws Exception {
        SecretKey secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] originalBytes = cipher.doFinal(decodedBytes);
        return new String(originalBytes);
    }

    // توليد توقيع HMAC
    private String generateHmac(String data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public Long findUser_idByToken(String token) {
        return jwtRepository.findByToken(token);
    }

    public tokenService() {
    }
}

