package com.example.FinancialManager.CronJobs;
import com.example.FinancialManager.History.TransactionHistoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class MonthlyResetJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryService.class);
    private final CronJobService cronJobService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("CronJob");
        cronJobService.monthlyResetJob();
    }
}
