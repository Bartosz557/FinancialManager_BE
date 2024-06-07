package com.example.FinancialManager.WebServices;

import com.example.FinancialManager.DAO.*;
import com.example.FinancialManager.DataModel.EnumTypes.ReminderType;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionStatus;
import com.example.FinancialManager.DataModel.EnumTypes.TransactionType;
import com.example.FinancialManager.DataModel.ExpenseCategories;
import com.example.FinancialManager.DataModel.RecurringExpenses;
import com.example.FinancialManager.DataModel.ScheduledExpenses;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestDTO;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.MainConfigModelDTO;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.SubCategoriesModelDTO;
import com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel.ExpenseModelDTO;
import com.example.FinancialManager.DTO.RequestBody.SettingChangeRequestDTO;
import com.example.FinancialManager.DTO.ResponseModel.ExpenseReminderDataResponseDTO;
import com.example.FinancialManager.DTO.ResponseModel.ExpenseReminderResponseDTO;
import com.example.FinancialManager.DTO.ResponseModel.UpcomingPaymentsResponseDTO;
import com.example.FinancialManager.DTO.ResponseModel.UserDetailsResponseDTO;
import com.example.FinancialManager.DTO.Converters.TransactionHistoryConverters;
import com.example.FinancialManager.DTO.RequestBody.TransactionRequestDTO;
import com.example.FinancialManager.DataModel.AccountDetails;
import com.example.FinancialManager.DataModel.LimitDetails;
import com.example.FinancialManager.DataModel.LimitDetailsId;
import com.example.FinancialManager.DataModel.UserData;
import com.example.FinancialManager.DataModel.EnumTypes.UserRole;
import com.example.FinancialManager.DataModel.TransactionHistory;
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
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    //FIXME: change every findByUsername(getContext()) into contextHolder.getPrincipal(). Maslo maslane idk XD
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
    private final TransactionHistoryConverters transactionHistoryConverters;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public boolean getConfigurationStatus(String email)
    {
        // FIXME: get userData to find userData by userData XDDDDDDDDDDDDDDDDDD 🙂🔫
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
        return "User has been registered successfully";
    }

    public String getUsers() {

        List<UserDetailsResponseDTO> userDetailsResponseDTOS = new ArrayList<>();
        if (userRepository.findByUserRole(UserRole.USER).isPresent()) {
            List<UserData> userDataList = userRepository.findByUserRole(UserRole.USER).get();
            for (UserData userData : userDataList){
                userDetailsResponseDTOS.add( new UserDetailsResponseDTO(
                        userData.getUsername(),
                        userData.getEmail(),
                        userData.getConfigured(),
                        userData.getEnabled()
                ));
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(userDetailsResponseDTOS);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error processing JSON";
        }
    }

    public boolean updateUserSetting(SettingChangeRequestDTO settingChangeRequestDTO, String setting) {
        UserData user = userRepository.findByUsername(settingChangeRequestDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + settingChangeRequestDTO.getUsername()));
        switch(setting) {
            case "username":
                user.setUsername(settingChangeRequestDTO.getNewValue());
                break;
            case "email":
                user.setEmail(settingChangeRequestDTO.getNewValue());
                break;
            case "password":
                user.setPassword(settingChangeRequestDTO.getNewValue());
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + settingChangeRequestDTO.getUsername());
        }
        userRepository.save(user);
        return true;
    }

    public int deleteUser(String username) {
        return userRepository.deleteByUsername(username);
//        return userRepository.deleteByUserID(6L);
    }

    public String deleteTransaction(int id){
        return "";
    }

    // TODO do smth wit this XDDD
    // TODO: Add proper validation to this and everywhere else tho
    public boolean setUserConfiguration(ConfigurationRequestDTO configurationRequestDTO) throws IllegalAccessException {
        boolean status = true;
        if(!setMainConfiguration(configurationRequestDTO.getMainConfig()))
            status = false;
        if(!addLimitDetails(configurationRequestDTO.getSubCategories()))
            status = false;
        if(configurationRequestDTO.getRepeatingExpenses().isRepeatingExpense())
            if(!addRecurringExpense(configurationRequestDTO.getRepeatingExpenses().getExpense()))
                status = false;
        return status;
    }

    public boolean addRecurringExpense(List<ExpenseModelDTO> expens) {
        logger.info("setRepeatingExpenses");
        RecurringExpenses recurringExpenses = new RecurringExpenses();
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        for( ExpenseModelDTO expenseModelDTO : expens){
            recurringExpenses.setUserDataRE(userData);
            recurringExpenses.setName(expenseModelDTO.getName());
            recurringExpenses.setDate(Integer.parseInt(expenseModelDTO.getDate()));
            recurringExpenses.setAmount(expenseModelDTO.getAmount());
            recurringExpenses.setReminderType(expenseModelDTO.getReminderType());
            recurringExpenses.setTransactionStatus(TransactionStatus.PENDING);
            recurringExpenses.setExpenseCategoriesID(expenseCategoriesRepository.findByCategoryName(expenseModelDTO.getCategory()).orElseThrow(() -> new RuntimeException("Category not found: "+ expenseModelDTO.getCategory())));
            recurringExpensesRepository.save(recurringExpenses);
        }
        return true;
    }

    public boolean addLimitDetails(SubCategoriesModelDTO subCategoriesModelDTO) throws IllegalAccessException {
        logger.info("setLimitDetails");
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Class<?> clazz = SubCategoriesModelDTO.class;
        Field[] fields = clazz.getDeclaredFields();
        int categoryLimit = 0;
        for (Field field : fields) {
            logger.info("Field type: " + field.getType());
            field.setAccessible(true);
            if(field.getType() == boolean.class) {
                if (!field.getBoolean(subCategoriesModelDTO))
                    return true;
            } else {
                if (field.getInt(subCategoriesModelDTO) == 0)
                    continue;
                logger.info("Field name: " + field.getName());
                logger.info("Field value: " + field.getInt(subCategoriesModelDTO));
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
                limitDetails.setLimitValue(field.getInt(subCategoriesModelDTO));
                categoryLimit += field.getInt(subCategoriesModelDTO);
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

    private boolean setMainConfiguration(MainConfigModelDTO mainConfigModelDTO) {
        logger.info("setMainConfiguration");
        AccountDetails accountDetails = new AccountDetails();
        userRepository.findByUsername(getContextUser().getName()).ifPresent(accountDetails::setUserDataAD);
        accountDetails.setSettlementDate(mainConfigModelDTO.getSettlementDate());
        accountDetails.setAccountBalance(mainConfigModelDTO.getAccountBalance());
        accountDetails.setMonthlyLimit(mainConfigModelDTO.getMonthlyLimit());
        accountDetails.setMonthlyIncome(mainConfigModelDTO.getMonthlyIncome());
        accountDetails.setMonthlySavings(0);
        accountDetails.setExpenses(0.0);
        accountDetails.setSavings(0);
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
    public String addTransaction(TransactionRequestDTO transactionRequestDTO) {
        String response = "";
        ExpenseCategories expenseCategories = expenseCategoriesRepository.findByCategoryName(
                transactionRequestDTO.getCategoryName()).orElseThrow(()
                -> new RuntimeException("Category not found"));
        UserData userData = userRepository.findByUsername(getContextUser().getName()).orElseThrow(()
                -> new RuntimeException("User not found"));
        TransactionHistory transactionHistory = new TransactionHistory(
                userData,
                expenseCategories,
                getCurrentDateFormatted(),
                transactionRequestDTO.getTransactionType(),
                transactionRequestDTO.getExpenseName(),
                transactionRequestDTO.getTransactionValue());
        transactionHistoryRepository.save(transactionHistory);
        AccountDetails accountDetails = accountDetailsRepository.findById(userData.getUserID()).orElseThrow(()
                -> new RuntimeException("Account details not found"));
         if(transactionRequestDTO.getTransactionType() == TransactionType.EXPENSE)
             response = addExpense(transactionRequestDTO.getTransactionValue(),accountDetails,response);
         else if (transactionRequestDTO.getTransactionType() == TransactionType.DEPOSIT)
             accountDetails.setAccountBalance(accountDetails.getAccountBalance()+ transactionRequestDTO.getTransactionValue());
         else
             return "Transaction type issue - action failed";
         try{
             accountDetailsRepository.save(accountDetails);
             response = "Transaction successful added to the account";
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

    public boolean addScheduledExpenses(ExpenseModelDTO expenseModelDTO) {
        ScheduledExpenses scheduledExpenses = new ScheduledExpenses(expenseModelDTO.getName(), expenseModelDTO.getDate(), expenseModelDTO.getAmount(), expenseModelDTO.getReminderType(), TransactionStatus.PENDING);
        scheduledExpenses.setExpenseCategoriesID(expenseCategoriesRepository.findByCategoryName(expenseModelDTO.getCategory()).orElseThrow(() -> new RuntimeException("Category not found")));

        userRepository.findByUsername(getContextUser().getName()).ifPresent(scheduledExpenses::setUserDataSE);
        try{
            scheduledExpensesRepository.save(scheduledExpenses);
            return true;
        }catch (Exception e){
            logger.error("Failed to save scheduled expense", e);
        }
        return false;
    }

    public ExpenseReminderResponseDTO checkForScheduledExpenses() {
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ExpenseReminderDataResponseDTO> today = new ArrayList<>();
        List<ExpenseReminderDataResponseDTO> tomorrow = new ArrayList<>();
        List<ExpenseReminderDataResponseDTO> nextWeek = new ArrayList<>();
        for( ScheduledExpenses scheduledExpense :  scheduledExpensesRepository.findAllByUserDataSEAndTransactionStatus(userData, TransactionStatus.PENDING)){
            logger.info("checking planned expense");
            checkExpense(
                    scheduledExpense.getReminderType(), 
                    new ExpenseReminderDataResponseDTO(
                            scheduledExpense.getName(), 
                            scheduledExpense.getExpenseCategoriesID().getCategoryName(), 
                            scheduledExpense.getAmount()
                    ), 
                    scheduledExpense.getDate(), 
                    new List[]{today, tomorrow, nextWeek}
            );
        }
        for( RecurringExpenses recurringExpense :  recurringExpensesRepository.findAllByUserDataRE(userData)){
            logger.info("checking recurring expense");
            checkExpense(
                    recurringExpense.getReminderType(),
                    new ExpenseReminderDataResponseDTO(
                            recurringExpense.getName(),
                            recurringExpense.getExpenseCategoriesID().getCategoryName(),
                            recurringExpense.getAmount()
                    ),
                    setNextRecurringExpenseDate(recurringExpense.getDate()),
                    new List[]{today, tomorrow, nextWeek}
            );
        }
        return new ExpenseReminderResponseDTO(today,tomorrow,nextWeek);
    }

    private String setNextRecurringExpenseDate(int date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate expenseDate = LocalDate.now().withDayOfMonth(date);
        if (date < LocalDate.now().getDayOfMonth()) {
            expenseDate = expenseDate.plusMonths(1);
        }
        return expenseDate.format(formatter);
    }

    private void checkExpense(ReminderType reminderType, ExpenseReminderDataResponseDTO expenseReminderDataResponseDTO, String date, List<ExpenseReminderDataResponseDTO>[] reminderList) {
        if(reminderType!=ReminderType.do_not_remind){
                if(checkTheDate(date,0)){
                    reminderList[0].add(expenseReminderDataResponseDTO);
                    return;
                }
                if(reminderType==ReminderType.two_reminders){
                    if(checkTheDate(date,1)) {
                        reminderList[1].add(expenseReminderDataResponseDTO);
                        logger.info("added reminder two");
                    }
                } else if (reminderType==ReminderType.three_reminders) {
                    if(checkTheDate(date,7)){
                        reminderList[2].add(expenseReminderDataResponseDTO);
                        return;
                    }
                    if(checkTheDate(date,1))
                        reminderList[1].add(expenseReminderDataResponseDTO);
                }
            }
    }


    private void addExpenseReminder(List<ExpenseReminderDataResponseDTO> list, ScheduledExpenses scheduledExpense) {
        list.add(new ExpenseReminderDataResponseDTO(

        ));
    }

    private boolean checkTheDate(String date, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = LocalDate.now();
        LocalDate reminderDate;
        try {
            reminderDate = LocalDate.parse(date, formatter);
            logger.info("Reminder date: "+ reminderDate + "today: " + today.plusDays(days));
            logger.info(String.valueOf(reminderDate.isEqual(today.plusDays(days))));
            return reminderDate.isEqual(today.plusDays(days));
        } catch (DateTimeParseException e) {
            logger.info("Invalid date format: " + date);
        }
        return false;
    }

    public List<UpcomingPaymentsResponseDTO> getAllUpcomingPayments() {
        UserData userData = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UpcomingPaymentsResponseDTO> upcomingPaymentsList = new ArrayList<>();
        List<RecurringExpenses> recurringExpenses = recurringExpensesRepository.findAllByUserDataREAndTransactionStatus(userData, TransactionStatus.PENDING);
        for( RecurringExpenses expense : recurringExpenses){
            upcomingPaymentsList.add(new UpcomingPaymentsResponseDTO(
                    transactionHistoryConverters.convertDateFormat(setNextRecurringExpenseDate(expense.getDate())),
                    expense.getAmount(),
                    expense.getName(),
                    expense.getReminderType()
                    ));
        }
        List<ScheduledExpenses> scheduledExpenses = scheduledExpensesRepository.findAllByUserDataSEAndTransactionStatus(userData, TransactionStatus.PENDING);
        for( ScheduledExpenses expense : scheduledExpenses){
            if(isPaymentInWithin(expense.getDate())){
                upcomingPaymentsList.add(new UpcomingPaymentsResponseDTO(
                        transactionHistoryConverters.convertDateFormat(expense.getDate()),
                        expense.getAmount(),
                        expense.getName(),
                        expense.getReminderType()
                ));
            }
        }
        return upcomingPaymentsList;
    }

    private boolean isPaymentInWithin(String targetDateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate targetDate = LocalDate.parse(targetDateString, formatter);
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(today, targetDate);
            logger.info("days between is equal:" + daysBetween);
            return daysBetween < 30;
        } catch (DateTimeParseException e) {
            logger.error("Error while calculating date period: " + e);
            return false;
        }
    }




//    public String getUsername() {
//        return
//    }
}
