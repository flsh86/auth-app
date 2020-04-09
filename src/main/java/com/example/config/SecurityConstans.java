package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

public class SecurityConstans {
    public static final long EXPIRATION_TIME = 864_000_000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/api/users";
    public static final String TOKEN_SECRET = "SecretKeyToGenJWT";
}
