package com.example.service;

import com.example.mapper.UserMapper;
import com.example.repository.ConfirmationTokenRepository;
import com.example.repository.UserRepository;
import com.example.token.ConfirmationToken;
import com.example.user.User;
import com.example.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
          return users.stream()
                  .map(userMapper::toDTO)
                  .collect(Collectors.toList());
        }
    }

    public UserDTO findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(
                u -> userMapper.toDTO(u)
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public UserDTO findByUserName(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        return user.map(
                u -> userMapper.toDTO(u)
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public void addUser(UserDTO dto) {
        if(dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> entity = userRepository.findById(dto.getId());

        if(entity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            User user = userMapper.toEntity(dto);
            userRepository.save(user);
            tokenService.registerUser(user);
        }
    }
}
