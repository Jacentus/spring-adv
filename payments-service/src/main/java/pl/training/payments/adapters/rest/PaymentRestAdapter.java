package pl.training.payments.adapters.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.payments.commons.Page;
import pl.training.payments.commons.web.LocationUri;
import pl.training.payments.commons.web.ResultPageDto;
import pl.training.payments.ports.PaymentService;

import static pl.training.payments.domain.PaymentStatus.STARTED;

@RequestMapping("payments")
@RestController
@RequiredArgsConstructor
public class PaymentRestAdapter {

    private final PaymentService paymentService;
    private final RestPaymentMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable String id) {
        var paymentDomain = paymentService.getById(id);
        var paymentDto = mapper.toDto(paymentDomain);
        return ResponseEntity.ok(paymentDto);
    }

   @PostMapping
   public ResponseEntity<PaymentDto> process(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        var paymentRequest = mapper.toDomain(paymentRequestDto);
        var paymentDomain = paymentService.process(paymentRequest);
        var paymentDto = mapper.toDto(paymentDomain);
        var locationUri = LocationUri.fromRequest(paymentDto.getId());
        return ResponseEntity.created(locationUri)
                .body(paymentDto);
   }

    @GetMapping("started")
    public ResponseEntity<ResultPageDto<PaymentDto>> getStartedPayments(
            @RequestParam(required = false, defaultValue = "0") int pageNumer,
            @RequestParam(required = false, defaultValue = "5") int pageSize) {
        var page = new Page(pageNumer, pageSize);
        var resultPage = paymentService.getByStatus(STARTED, page);
        var resultPageDto = mapper.toDto(resultPage);
        return ResponseEntity.ok(resultPageDto);
    }

}
