package com.zhang.bean;

import org.apache.shiro.authc.AuthenticationToken;

// token
public class AuthToken implements AuthenticationToken {
    private final String token;
    public AuthToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal(){
        return token;
    }

    @Override
    public Object getCredentials(){
        return token;
    }
}

