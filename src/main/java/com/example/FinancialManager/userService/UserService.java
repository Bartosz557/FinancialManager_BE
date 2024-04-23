package com.example.FinancialManager.userService;

import com.example.FinancialManager.ProfileDashboard.TransactionForm;
import com.example.FinancialManager.database.Repositories.*;
import com.example.FinancialManager.database.accountDetails.AccountDetails;
import com.example.FinancialManager.database.accountDetails.LimitDetails;
import com.example.FinancialManager.database.accountDetails.LimitDetailsId;
import com.example.FinancialManager.database.transactions.*;
import com.example.FinancialManager.database.user.UserData;
import com.example.FinancialManager.database.user.UserRole;
import com.example.FinancialManager.database.userHistory.TransactionHistory;
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
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final ScheduledExpensesRepository scheduledExpensesRepository;

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

    // TODO do smth wit this XDDD
    public boolean setUserConfiguration(ConfigurationForm configurationForm) throws IllegalAccessException {
        boolean status = true;
        if(!setMainConfiguration(configurationForm.getMainConfig()))
            status = false;
        if(!addLimitDetails(configurationForm.getSubCategories()))
            status = false;
        if(configurationForm.getRepeatingExpenses().isRepeatingExpense())
            if(!addRecurringExpense(configurationForm.getRepeatingExpenses().getExpenses()))
                status = false;
        return status;
    }

    public boolean addRecurringExpense(List<Expense> expenses) {
        logger.info("setRepeatingExpenses");
        RecurringExpenses recurringExpenses = new RecurringExpenses();
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
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

    public boolean addLimitDetails(SubCategories subCategories) throws IllegalAccessException {
        logger.info("setLimitDetails");
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Class<?> clazz = SubCategories.class;
        Field[] fields = clazz.getDeclaredFields();
        int categoryLimit = 0;
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
                categoryLimit += field.getInt(subCategories);
                logger.info(limitDetails.toString());
                limitDetailsRepository.save(limitDetails);
            }
            setOtherLimit(categoryLimit, userData);
        }
        return true;
    }

    private void setOtherLimit(int categoryLimit, UserData userData) {
        AccountDetails accountDetails = accountDetailsRepository.findById(userData.getUserID()).orElseThrow(() -> new RuntimeException("Account details not found"));
        LimitDetailsId limitDetailsId = new LimitDetailsId();
        limitDetailsId.setUserID(userData.getUserID());
        ExpenseCategories expenseCategories =  expenseCategoriesRepository.findByCategoryName("other").orElseThrow(() -> new RuntimeException("Category details not found"));
        limitDetailsId.setCategoryID(expenseCategories.getCategoryId());
        LimitDetails limitDetails = new LimitDetails();
        limitDetails.setId(limitDetailsId);
        limitDetails.setUserDataLD(userData);
        limitDetails.setExpenseCategoriesID(expenseCategories);
        limitDetails.setLimitValue(accountDetails.getMonthlyLimit()-categoryLimit);
        limitDetailsRepository.save(limitDetails);
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

    @Transactional
    public String addTransaction(TransactionForm transactionForm) {
        String response = "ok";
        ExpenseCategories expenseCategories = expenseCategoriesRepository.findByCategoryName(transactionForm.getCategoryName()).orElseThrow(() -> new RuntimeException("Category not found"));
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TransactionHistory transactionHistory = new TransactionHistory(
                userData,
                expenseCategories,
                getCurrentDateFormatted(),
                transactionForm.getTransactionType(),
                transactionForm.getExpenseName(),
                transactionForm.getTransactionValue());
        transactionHistoryRepository.save(transactionHistory);
        AccountDetails accountDetails = accountDetailsRepository.findById(userData.getUserID()).orElseThrow(() -> new RuntimeException("Account details not found"));
         if(transactionForm.getTransactionType() == TransactionType.EXPENSE)
             response = addExpense(transactionForm.getTransactionValue(),accountDetails,response);
         else if (transactionForm.getTransactionType() == TransactionType.DEPOSIT)
             accountDetails.setAccountBalance(accountDetails.getAccountBalance()+transactionForm.getTransactionValue());
         else
             return "Transaction type issue - action failed";
         try{
             accountDetailsRepository.save(accountDetails);
         }catch (Exception e){
             logger.error("Failed to save account details", e);
         }
        return response;
    }
    private String addExpense(double value, AccountDetails accountDetails, String response) {
        accountDetails.setAccountBalance(accountDetails.getAccountBalance()-value);
        accountDetails.setExpenses(accountDetails.getExpenses()+value);
        if(accountDetails.getExpenses()<0.0)
            response = response + "Your account has reached negative balance!";
        return response;
    }

    private String getCurrentDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(formatter);
    }

    public boolean addScheduledExpenses(ScheduledExpenses scheduledExpenses) {
        userRepository.findByUsername(getContextUser().getName()).ifPresent(scheduledExpenses::setUserDataSE);
        try{
            scheduledExpensesRepository.save(scheduledExpenses);
            return true;
        }catch (Exception e){
            logger.error("Failed to save scheduled expense", e);
        }
        return false;
    }


//    public String getUsername() {
//        return
//    }
}
