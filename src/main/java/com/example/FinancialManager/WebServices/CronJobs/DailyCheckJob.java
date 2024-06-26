package com.example.FinancialManager.WebServices.CronJobs;
import com.example.FinancialManager.WebServices.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class DailyCheckJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryService.class);
    private final CronJobService cronJobService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("DailyCheckJob executed");
        cronJobService.monthlyResetJob();
    }
}



