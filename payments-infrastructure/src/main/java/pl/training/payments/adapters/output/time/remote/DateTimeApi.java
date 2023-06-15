package pl.training.payments.adapters.output.time.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dateTimeApi", url = "${dateTimeApi}")
public interface DateTimeApi {

    @GetMapping
    DateTimeDto getDateTime();

}
