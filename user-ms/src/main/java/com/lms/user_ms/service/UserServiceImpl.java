package com.lms.user_ms.service;

import com.lms.user_ms.exception.ValidationException;
import com.lms.user_ms.model.User;
import com.lms.user_ms.dto.UserWrapper;
import com.lms.user_ms.repository.UserRepository;
import com.lms.user_ms.utilities.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtility passwordUtility;

    private String validateUsername(String username) {
        String errorMsg = "";
        if(username == null || username.isEmpty()) {
            errorMsg = "Username is required. ";
        }
        return errorMsg;
    }

    private String validateEmail(String email) {
        String errorMsg = "";
        if(email == null || email.isEmpty() || email.isBlank()) {
            errorMsg = "Email is required. ";
            if(email == null) {
                return errorMsg;
            }
        }

        Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.com$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex.matcher(email);

        if(!matcher.matches()) {
            errorMsg += "Email should contain '@' and '.com'. ";
        }

        return errorMsg;
    }

    private String validatePassword(String password) {
        String errorMsg = "";
        if(password == null || password.isEmpty() || password.isBlank()) {
            errorMsg = "Password is required. ";
            if(password == null) {
                return errorMsg;
            }
        }

        if(password.length() < 8) {
            errorMsg += "Password should be at least 8 characters. ";
        }

        Pattern passwordRegex = Pattern.compile("[a-zA-Z]+\\d+");
        Matcher matcher = passwordRegex.matcher(password.toLowerCase());

        if(!matcher.matches()) {
            errorMsg += "Password should be alphanumeric. ";
        }

        return errorMsg;
    }


    @Override
    public void validateAllFields(UserWrapper userWrapper) throws ValidationException {
        StringBuilder errorMsgSummary = new StringBuilder();
        errorMsgSummary.append(validateUsername(userWrapper.getUsername()));
        errorMsgSummary.append(validateEmail(userWrapper.getEmail()));
        errorMsgSummary.append(validatePassword(userWrapper.getPassword()));
        if(errorMsgSummary != null && errorMsgSummary.length() > 0) {
            throw new ValidationException(errorMsgSummary.toString());
        }
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
