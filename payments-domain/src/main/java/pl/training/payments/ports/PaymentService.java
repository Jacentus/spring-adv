package pl.training.payments.ports;

import pl.training.payments.commons.Page;
import pl.training.payments.commons.ResultPage;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRequest;
import pl.training.payments.domain.PaymentStatus;

public interface PaymentService {

    Payment process(PaymentRequest paymentRequest);

    Payment getById(String id);

    ResultPage<Payment> getByStatus(PaymentStatus paymentStatus, Page page);

}
