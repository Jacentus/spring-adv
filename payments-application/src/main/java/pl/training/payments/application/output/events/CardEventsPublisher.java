package pl.training.payments.application.output.events;

public interface CardEventsPublisher {

    void publish(CardChargedApplicationEvent event);

}
