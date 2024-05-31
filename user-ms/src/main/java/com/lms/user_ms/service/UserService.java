package com.lms.user_ms.service;

import com.lms.user_ms.exception.ValidationException;
import com.lms.user_ms.dto.UserWrapper;

public interface UserService {

    void validateAllFields(UserWrapper userWrapper) throws ValidationException;

    UserWrapper saveUser(UserWrapper requestBody) throws ValidationException;


}
