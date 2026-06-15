package com.seichigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seichigo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    // ID 중복 확인
    @GetMapping("/checkId")
    public ResponseEntity<?> checkId(@RequestParam("id") String id) {
        boolean exists = userService.checkIdExists(id);
        return ResponseEntity.ok(exists);
    }
}
