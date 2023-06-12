package pl.training.payments.adapters.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.input.commands.ChargeCardFeesCommandHandler;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class CardFeesController {

    private final ChargeCardFeesCommandHandler chargeCardFeesCommandHandler;
    private final RestPaymentMapper mapper;

    @PostMapping("fees")
    public ResponseEntity<Void> chargeCardFees(@RequestBody ChargeFeesDto chargeFeesDto) {
        var chargeCardCommand = mapper.toDomain(chargeFeesDto);
        chargeCardFeesCommandHandler.handle(chargeCardCommand);
        return ResponseEntity.noContent().build();
    }

}
