package pl.training.payments.adapters.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;

@RestController
@RequiredArgsConstructor
public class CardChargesController {

    private final ChargeCardCommandHandler chargeCardCommandHandler;
    private final RestPaymentMapper mapper;

    @PostMapping("cards/charges")
    public ResponseEntity<Void> chargeCard(@RequestBody ChargeRequestDto chargeRequestDto) {
        var chargeCard = mapper.toDomain(chargeRequestDto);
        chargeCardCommandHandler.handle(chargeCard);
        return ResponseEntity.noContent().build();
    }

}
