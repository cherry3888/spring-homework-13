package ru.cherry.springhomework.security;

import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cherry.springhomework.dao.UserRepository;
import ru.cherry.springhomework.domain.User;

import java.util.Optional;


@Service
@Data
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userO = userRepository.findUserByUsername(username);
        if (userO.isEmpty()) {
            throw new InternalAuthenticationServiceException("User not found");
        }
        return userO.get();
    }
}
