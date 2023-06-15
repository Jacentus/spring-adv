package pl.training.chat.integration.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Log
@RequiredArgsConstructor
public class SchedulerInitializer implements ApplicationRunner {

    private final TaskScheduler customScheduler;

    @Override
    public void run(ApplicationArguments args)  {
        // customScheduler.scheduleAtFixedRate(() -> log.info("Running task"), Duration.ofSeconds(5));
        customScheduler.schedule(() -> log.info("Running task"), new CronTrigger("0 15 9-17 * * MON-FRI"));
    }

}
