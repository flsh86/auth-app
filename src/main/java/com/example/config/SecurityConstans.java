package com.example.config;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstans {
    public static final long EXPIRATION_TIME = 864_000_000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/api/users";
    public static final String TOKEN_SECRET = "SecretKeyToGenJWT";
}
