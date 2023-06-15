package pl.training.chat.integration.scheduling;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log
public class StatusLogger {

    // @Scheduled(cron = "@daily")
    // @Scheduled(cron = "0 */1 * * * *")
    // @Scheduled(fixedRate = 5_000)
    public void showStatus() {
        log.info("Current status: ok " + Instant.now());
    }

}
