package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransActionRequestDto {
    private int count;
    private int saleId;
    private int id;
   private double productPrice;
   private int productId;
}
