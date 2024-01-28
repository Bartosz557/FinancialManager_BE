package com.example.FinancialManager.userService;

import com.example.FinancialManager.database.Repositories.UserRepository;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public boolean getConfigurationStatus(String email)
    {
        Boolean status;
        Optional<UserData> userData = userRepository.findByEmail(email);
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
        boolean userExists = userRepository.findByEmail(userData.getEmail()).isPresent();
        if(userExists)
        {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userData.getPassword());
        userData.setPassword(encodedPassword);
        userRepository.save(userData);
        return "";
    }

    public String getUsers() {

        List<UserDetailsForm> userDetailsForms = new ArrayList<>();
        if (userRepository.findByUserRole(UserRole.USER).isPresent()) {
            List<UserData> userDataList = userRepository.findByUserRole(UserRole.USER).get();
            for (UserData userData : userDataList){
                userDetailsForms.add( new UserDetailsForm(
                        userData.getUsername(),
                        userData.getEmail(),
                        userData.getConfigured(),
                        userData.getEnabled()
                ));
            }
        }
        // Convert userDetailsForms to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(userDetailsForms);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error processing JSON";
        }
    }

    public boolean updateUserSetting(SettingChangeREquest settingChangeREquest, String setting) {
        UserData user = userRepository.findByUsername(settingChangeREquest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + settingChangeREquest.getUsername()));
        switch(setting) {
            case "username":
                user.setUsername(settingChangeREquest.getNewValue());
                break;
            case "email":
                user.setEmail(settingChangeREquest.getNewValue());
                break;
            case "password":
                user.setPassword(settingChangeREquest.getNewValue());
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + settingChangeREquest.getUsername());
        }
        userRepository.save(user);
        return true;
    }

    public int deleteUser(String username) {
        return userRepository.deleteByUsername(username);
//        return userRepository.deleteByUserID(6L);
    }
}
