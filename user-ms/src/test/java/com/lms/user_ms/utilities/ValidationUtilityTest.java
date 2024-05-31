package com.lms.user_ms.utilities;

import com.lms.user_ms.exception.ValidationException;
import com.lms.user_ms.dto.UserWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidationUtilityTest {

    @InjectMocks
    private ValidationUtility validationUtility;

    private UserWrapper userWrapper;

    @BeforeEach
    public void init() { userWrapper = new UserWrapper(); }

    @Test
    public void testValidateAllFieldsPos() throws ValidationException {
        userWrapper.setUsername("chi");
        userWrapper.setEmail("chit@test.com");
        userWrapper.setPassword("Sample123");
        testValidateAllFieldsPositiveScenario(userWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg() throws ValidationException {
        userWrapper.setUsername(null);
        userWrapper.setEmail("chittest.com");
        userWrapper.setPassword("Sampleeee");
        testValidateAllFieldsNegativeScenario(userWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg2() throws ValidationException {
        userWrapper.setUsername("");
        userWrapper.setEmail("chit@testcom");
        userWrapper.setPassword("Sample");
        testValidateAllFieldsNegativeScenario(userWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg3() throws ValidationException {
        userWrapper.setUsername("aaa");
        userWrapper.setEmail("");
        userWrapper.setPassword("");
        testValidateAllFieldsNegativeScenario(userWrapper);
    }

    @Test
    public void testValidateAllFieldsNeg4() throws ValidationException {
        userWrapper.setUsername("aaa");
        userWrapper.setEmail(null);
        userWrapper.setPassword(null);
        testValidateAllFieldsNegativeScenario(userWrapper);
    }

    private void testValidateAllFieldsPositiveScenario(UserWrapper requestBody) throws ValidationException {
        assertDoesNotThrow(() -> validationUtility.validateAllFields(requestBody));
    }

    private void testValidateAllFieldsNegativeScenario(UserWrapper requestBody) throws ValidationException {
        assertThrows(ValidationException.class, () -> validationUtility.validateAllFields(requestBody));
    }
}
