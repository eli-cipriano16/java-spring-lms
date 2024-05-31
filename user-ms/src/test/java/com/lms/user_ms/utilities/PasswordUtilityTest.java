package com.lms.user_ms.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordUtilityTest {

    @InjectMocks
    private PasswordUtility passwordUtility;

    private final String password = "password";
    @Test
    public void testEncryptPassword() {
        String encryptedPassword = passwordUtility.getEncryptedPw(password);
        assertNotNull(encryptedPassword);
        assertNotEquals(encryptedPassword, password);
        boolean isValidPass = passwordUtility.checkPassword(password, encryptedPassword);
        assertTrue(isValidPass);
    }


}
