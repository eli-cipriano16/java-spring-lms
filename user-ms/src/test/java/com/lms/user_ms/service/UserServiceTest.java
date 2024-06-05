package com.lms.user_ms.service;

import com.lms.user_ms.model.User;
import com.lms.user_ms.dto.UserWrapper;
import com.lms.user_ms.repository.UserRepository;
import com.lms.user_ms.utilities.PasswordUtility;
import com.lms.user_ms.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtility passwordUtility;

    @InjectMocks
    private UserServiceImpl userService;

    private UserWrapper userWrapper;

    @BeforeEach
    public void init() {
        userWrapper = new UserWrapper();
    }

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


    @Test
    public void testSaveTeacher() throws ValidationException{
        User user = new User();
        UserWrapper userWrapper = new UserWrapper("chit", "chit@test.com", "Sample123");
        user.setUsername(userWrapper.getUsername());
        user.setEmail(userWrapper.getEmail());
        user.setPassword(passwordUtility.getEncryptedPw(userWrapper.getPassword()));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserWrapper savedUser = userService.saveUser(userWrapper);
        Assertions.assertThat(savedUser).isNotNull();
    }

    private void testValidateAllFieldsPositiveScenario(UserWrapper requestBody) throws ValidationException {
        assertDoesNotThrow(() -> userService.validateAllFields(requestBody));
    }

    private void testValidateAllFieldsNegativeScenario(UserWrapper requestBody) throws ValidationException {
        assertThrows(ValidationException.class, () -> userService.validateAllFields(requestBody));
    }
}
