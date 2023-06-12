package pl.training.payments.domain.common;

public interface Policy<R> {

    R execute();

}
