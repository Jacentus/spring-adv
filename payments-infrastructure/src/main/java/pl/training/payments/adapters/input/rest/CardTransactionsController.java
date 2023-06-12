package pl.training.payments.adapters.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.input.queries.GetTransactionsQueryHandler;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardTransactionsController {

    private final GetTransactionsQueryHandler getTransactionsQueryHandler;
    private final RestPaymentMapper mapper;

    @GetMapping("cards/{number}/transactions")
    public ResponseEntity<List<CardTransactionDto>> getTransactions(@PathVariable String number) {
        var getTransactions = mapper.toDomain(number);
        var transactions = getTransactionsQueryHandler.handle(getTransactions);
        var transactionDtos = mapper.toDtos(transactions);
        return ResponseEntity.ok(transactionDtos);
    }

}
