package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Client;
import com.quiz.quiz.entity.Sale;
import com.quiz.quiz.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data //A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDto {

    private int id;
    private String seller;
    private double total;
    private String creationDate;
    private Client client;
    private List<TransActionResponseDto> transactions;
    public SaleResponseDto(Sale sale, List<TransActionResponseDto> transactions,Client client){
     this.id=sale.getId();
     this.transactions=transactions;
     this.seller=sale.getSeller();
     this.total=sale.getTotal();
     this.creationDate=sale.getCreationDate();
     this.client=client;
    }
}