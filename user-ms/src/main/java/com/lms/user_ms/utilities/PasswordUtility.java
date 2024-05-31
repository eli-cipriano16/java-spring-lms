package com.lms.user_ms.utilities;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordUtility {

    public String getEncryptedPw(String password) {
        String salt = BCrypt.gensalt(15);
        byte[] encodedPw = password.getBytes(StandardCharsets.UTF_8);
        String hashedPw = BCrypt.hashpw(encodedPw, salt);
        return hashedPw;
    }

    public boolean checkPassword(String currentPw, String pwEntered) {
        return BCrypt.checkpw(currentPw, pwEntered);
    }

}
