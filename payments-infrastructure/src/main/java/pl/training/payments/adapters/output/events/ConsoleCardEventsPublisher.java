package pl.training.payments.adapters.output.events;

import lombok.extern.java.Log;
import pl.training.payments.application.output.events.CardChargedEvent;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.common.Adapter;

@Log
@Adapter
public class ConsoleCardEventsPublisher implements CardEventsPublisher {

    @Override
    public void publish(CardChargedEvent event) {
        log.info("New application event: " + event);
    }

}
