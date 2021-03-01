package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;


@Data //A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestDto {



    private int id;
    private String seller;
    private double total;
    private int clientId;
private List<TransActionRequestDto> transactions;
}