package com.lms.user_ms.controller;

import com.lms.user_ms.exception.InternalServerException;
import com.lms.user_ms.exception.ValidationException;
import com.lms.user_ms.dto.ErrorMessage;
import com.lms.user_ms.dto.UserWrapper;
import com.lms.user_ms.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lms/user/")
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController(UserService userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserWrapper userWrapper) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userWrapper));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
        } catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"));
        }
    }
}
