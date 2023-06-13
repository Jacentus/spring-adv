package pl.training.payments.adapters.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;

@RestController
@RequestMapping("cards")
@RequiredArgsConstructor
public class CardChargesController {

    private final ChargeCardCommandHandler chargeCardCommandHandler;
    private final RestPaymentMapper mapper;

    @PostMapping("charges")
    public ResponseEntity<Void> chargeCard(@Valid @RequestBody ChargeRequestDto chargeRequestDto) {
        var chargeCardCommand = mapper.toDomain(chargeRequestDto);
        chargeCardCommandHandler.handle(chargeCardCommand);
        return ResponseEntity.noContent().build();
    }

}
