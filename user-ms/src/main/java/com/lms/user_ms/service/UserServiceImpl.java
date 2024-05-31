package com.lms.user_ms.service;

import com.lms.user_ms.exception.ValidationException;
import com.lms.user_ms.model.User;
import com.lms.user_ms.dto.UserWrapper;
import com.lms.user_ms.repository.UserRepository;
import com.lms.user_ms.utilities.PasswordUtility;
import com.lms.user_ms.utilities.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtility passwordUtility;

    @Autowired
    private ValidationUtility validationUtility;

    @Override
    public void validateAllFields(UserWrapper userWrapper) throws ValidationException {
        validationUtility.validateAllFields(userWrapper);
    }

    @Override
    public UserWrapper saveUser(UserWrapper requestBody) throws ValidationException {
        validateAllFields(requestBody);
        User user = new User();
        user.setUsername(requestBody.getUsername());
        user.setEmail(requestBody.getEmail());
        user.setPassword(passwordUtility.getEncryptedPw(requestBody.getPassword()));
        User savedUser = userRepository.save(user);

        return requestBody;
    }
}
