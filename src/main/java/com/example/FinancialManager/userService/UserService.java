package com.example.FinancialManager.userService;

import com.example.FinancialManager.AdminCockpit.AdminController;
import com.example.FinancialManager.database.Repositories.*;
import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.accountDetails.LimitDetails;
import com.example.FinancialManager.database.accountDetails.LimitDetailsId;
import com.example.FinancialManager.database.transactions.ExpenseCategories;
import com.example.FinancialManager.database.transactions.RecurringExpenses;
import com.example.FinancialManager.database.transactions.ReminderType;
import com.example.FinancialManager.database.transactions.TransactionStatus;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;
    private final LimitDetailsRepository limitDetailsRepository;
    private final RecurringExpensesRepository recurringExpensesRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public boolean getConfigurationStatus(String email)
    {
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return userData.getConfigured();
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

    public boolean setUserConfiguration(ConfigurationForm configurationForm) throws IllegalAccessException {
        return setMainConfiguration(configurationForm.getMainConfig()) &&
                setLimitDetails(configurationForm.getSubCategories()) &&
                setRepeatingExpenses(configurationForm.getRepeatingExpenses());
    }

    private boolean setRepeatingExpenses(RepeatingExpenses repeatingExpenses) {
        logger.info("setRepeatingExpenses");
        if(!repeatingExpenses.isRepeatingExpense())
            return true;
        RecurringExpenses recurringExpenses = new RecurringExpenses();
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Expense> expenses = repeatingExpenses.getExpenses();
        for( Expense expense : expenses){
            recurringExpenses.setUserDataRE(userData);
            recurringExpenses.setName(expense.getName());
            recurringExpenses.setDate(expense.getDate());
            recurringExpenses.setAmount(expense.getAmount());
            recurringExpenses.setReminderType(ReminderType.two_reminders);
            recurringExpenses.setTransactionStatus(TransactionStatus.PENDING);
            recurringExpensesRepository.save(recurringExpenses);
        }
        return true;
    }

    private boolean setLimitDetails(SubCategories subCategories) throws IllegalAccessException {
        logger.info("setLimitDetails");
        if(!subCategories.isExpenseCategories())
            return true;
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Class<?> clazz = SubCategories.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.info("Field type: " + field.getType());
            field.setAccessible(true);
            if(field.getType() == boolean.class) {
                if (!field.getBoolean(subCategories))
                    return true;
            } else {
                if (field.getInt(subCategories) == 0)
                    continue;
                logger.info("Field name: " + field.getName());
                logger.info("Field value: " + field.getInt(subCategories));
                LimitDetails limitDetails = new LimitDetails();
                LimitDetailsId limitDetailsId = new LimitDetailsId();
                limitDetailsId.setUserID(userData.getUserID());
                ExpenseCategories expenseCategories = expenseCategoriesRepository.findByCategoryName(field.getName()).orElseThrow(() -> new RuntimeException("User not found"));
                limitDetailsId.setCategoryID(expenseCategories.getCategoryId());
                limitDetails.setId(limitDetailsId);
                limitDetails.setUserDataLD(userData);
                limitDetails.setExpenseCategoriesID(expenseCategories);
                expenseCategoriesRepository.findByCategoryName(field.getName()).ifPresent(expenseCategory -> {
                    logger.info("Expense category found: " + expenseCategory.getCategoryId());
                });
                limitDetails.setLimitValue(field.getInt(subCategories));
                logger.info(limitDetails.toString());
                limitDetailsRepository.save(limitDetails);
            }
        }
        return true;
    }

    private boolean setMainConfiguration(MainConfig mainConfig) {
        logger.info("setMainConfiguration");
        AccountDetails accountDetails = new AccountDetails();
        userRepository.findByUsername(getContextUser().getName()).ifPresent(accountDetails::setUserDataAD);
        accountDetails.setSettlementDate(mainConfig.getSettlementDate());
        accountDetails.setAccountBalance(mainConfig.getAccountBalance());
        accountDetails.setMonthlyLimit(mainConfig.getMonthlyLimit());
        accountDetails.setMonthlyIncome(mainConfig.getMonthlyIncome());
        accountDetails.setExpenses(0.0);
        accountDetails.setSavings(0.0);
        accountDetails.setResidualFunds(0.0);
        accountDetailsRepository.save(accountDetails);
        return true;
    }

    private Authentication getContextUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void userConfiguredSet() {
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        userData.setConfigured(true);
        userRepository.save(userData);
    }

//    public String getUsername() {
//        return
//    }
}
