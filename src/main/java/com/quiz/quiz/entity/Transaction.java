package com.quiz.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import  com.quiz.quiz.entity.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {


    @Id
    @GeneratedValue
    private int id;

    private int count;
    @ManyToOne
    @JoinColumn(name="sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", count=" + count +
                ", product=" + product.toString() +
                '}';
    }

    public String compare(Transaction t){
        String result="";
        if (t.count!=this.count){
            result="the count is updated from:"+this.getCount()+" to:"+t.getCount();
        }

        return result;
    }

}
