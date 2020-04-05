package com.example.service;

import com.example.repository.ConfirmationTokenRepository;
import com.example.repository.UserRepository;
import com.example.token.ConfirmationToken;
import com.example.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailService emailService;
    private UserRepository userRepository;

    @Autowired
    public TokenService(ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService, UserRepository userRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Activate your account");
        simpleMailMessage.setFrom("my@mail.com");
        simpleMailMessage.setText("To confirm your account, please click here : " +
                "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailService.sendMail(simpleMailMessage);
    }

    public void confirmAccount(String token) {
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByConfirmationToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token was not found"));

        LocalDateTime createdTime = confirmationToken.getCreateDate();
        boolean isExpired = Duration.between(createdTime, LocalDateTime.now()).toMinutes() > 5;

        if (!isExpired) {
            String userEmail = confirmationToken.getUser().getEmail();
            Optional<User> user = userRepository.findByEmail(userEmail);
            user.ifPresentOrElse(u -> {
                u.setEnabled(true);
                userRepository.save(u);
            }, () -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
            });
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
    }

    public void reSendRegistrationToken(String existingToken) {
        if(existingToken == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByConfirmationToken(existingToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token was not found"));

        User user = userRepository
                .findByEmail(confirmationToken.getUser().getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found"));

        if(!user.getEnabled()) {
            confirmationTokenRepository.delete(confirmationToken);
            registerUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already active");
        }
    }
}
