package com.example.FinancialManager.WebServices.CronJobs;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail dailyJobDetail() {
        return JobBuilder.newJob(DailyCheckJob.class)
                .withIdentity("DailyCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public CronTrigger dailyJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(dailyJobDetail())
                .withIdentity("dailyJobDetail")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }
}



