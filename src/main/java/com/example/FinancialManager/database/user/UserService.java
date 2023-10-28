package com.example.FinancialManager.database.user;

import com.example.FinancialManager.database.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(UserData userData)
    {
        boolean userExists = appUserRepository.findByEmail(userData.getEmail()).isPresent();
        if(userExists)
        {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userData.getPassword());
        userData.setPassword(encodedPassword);
        appUserRepository.save(userData);
        return "";
    }
}
