package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransActionResponseDto {
    private int count;

   private Product product;
}
