package com.neeraj.assistant.common.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.neeraj.assistant.user.entity.User;

public class SecurityUtils {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}