package com.example.FinancialManager.CronJobs;
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
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(DailyCheckJob.class)
                .withIdentity("DailyCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public CronTrigger myJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // 5 seconds period test schedule
                .build();
    }
}
