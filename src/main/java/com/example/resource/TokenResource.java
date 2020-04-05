package com.example.resource;

import com.example.service.TokenService;
import com.example.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenResource {
    private TokenService tokenService;

    @Autowired
    public TokenResource(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping(path = "/confirm-account", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        tokenService.confirmAccount(token);
        return ResponseEntity.ok("Congratulation you have activated your account successfully");
    }

    @GetMapping(path = "/resendRegistrationToken")
    public ResponseEntity<?> resendRegistrationToken(@RequestParam(name = "token") String existingToken) {
        tokenService.reSendRegistrationToken(existingToken);
        return ResponseEntity.ok("Check your email for new registration link");
    }
}
