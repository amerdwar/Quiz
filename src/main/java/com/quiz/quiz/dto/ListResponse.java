package com.quiz.quiz.dto;

import com.quiz.quiz.utils.ResponeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ListResponse <T>{

    ResponeStatus status;
    String message;
    long totalRecordesCount;
    int numberOfPages;
    int currentPage;
    int pageSize;
    List<T> data;
    public ListResponse(ResponeStatus status,String message){
        this.status=status;
        this.message=message;
    }
}
