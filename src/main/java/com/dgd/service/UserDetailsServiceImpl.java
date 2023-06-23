package com.dgd.service;

import java.util.HashSet;
import java.util.Set;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.model.entity.User;
import com.dgd.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.dgd.exception.message.AuthErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws AuthenticationException {
        System.out.println("userId in loadUserByUsername = " + userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getUserId(), user.getPassword(), grantedAuthorities);
    }
}