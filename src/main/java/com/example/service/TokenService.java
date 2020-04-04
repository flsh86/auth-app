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

        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);

//        Date expire = confirmationToken
//                .map(ConfirmationToken::getCreateDate)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String userEmail = confirmationToken
//                .flatMap(c -> {
//                    if(c.getCreateDate().after(c.getCreateDate().getTime() +))
//                })
                .map(ConfirmationToken::getUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST))
                .getEmail();



        Optional<User> user = userRepository.findByEmail(userEmail);
        user.ifPresentOrElse(u -> {
                    u.setEnabled(true);
                    userRepository.save(u);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that email was not found");
                });
    }

}
