package com.example.FinancialManager.userService;

import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean getConfigurationStatus(String email)
    {
        Boolean status;
        Optional<UserData> userData = appUserRepository.findByEmail(email);
        if (userData.isEmpty()) {
            UserData data = userData.get();
            status = data.getConfigured();
            return status;
        } else {
            throw new IllegalStateException("User not found for email: " + email);
        }
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

    public List<UserDetailsForm> getUsers(){

        List<UserDetailsForm> userDetailsForms = new ArrayList<>();
        if (appUserRepository.findByUserRole(UserRole.ADMIN.name()).isPresent()) {
            List<UserData> userDataList = appUserRepository.findByUserRole(UserRole.ADMIN.name()).get();
            for (UserData userData : userDataList){
                userDetailsForms.add( new UserDetailsForm(
                        userData.getUsername(),
                        userData.getEmail(),
                        userData.getConfigured(),
                        userData.getEnabled()
                ));
            }
        }
        return userDetailsForms;
    }
}
