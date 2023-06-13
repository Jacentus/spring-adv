package pl.training.payments.adapters.output.persistence.jpa;

import org.springframework.beans.factory.annotation.Value;

public interface CardProjection {

    String getNumber();
    @Value("#{target.balance + ' ' + target.currencyCode}")
    String getBalance();

}
