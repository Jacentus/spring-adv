package pl.training.payments.commons.web;

import lombok.Value;

import java.util.List;

@Value
public class ResultPageDto<T> {

    List<T> data;
    int pageNumber;
    long totalPages;

}
