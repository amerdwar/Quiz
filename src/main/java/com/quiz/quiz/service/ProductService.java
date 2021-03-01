package com.quiz.quiz.service;

import com.quiz.quiz.dto.ListResponse;
import com.quiz.quiz.dto.ObjectResponse;
import com.quiz.quiz.entity.Product;
import com.quiz.quiz.repository.ProductRepository;
import com.quiz.quiz.utils.ResponeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public  ListResponse<Product>  getAllProducts(int pageSize,int pageNumber){
        try {
            Sort sort = Sort.by(
                    Sort.Order.asc("name"));
            if (pageSize == -1) {
                List<Product> data = productRepository.findAll(sort);
                ListResponse<Product> response = new ListResponse<Product>(ResponeStatus.SUCCESS, "", data.size(), 1, 0, data.size(), data);
                return response;
            } else {
                Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
                Page page = productRepository.findAll(paging);

                ListResponse<Product> response = new ListResponse<Product>(ResponeStatus.SUCCESS, "", page.getTotalElements(), page.getTotalPages(), pageNumber, pageSize, page.getContent());

                return response;
            }
        } catch (Exception e){
                return   new ListResponse<Product>(ResponeStatus.ERROR,e.toString());
            }
    }

    public ObjectResponse<Product> createProduct(Product product){
       try{
        Product result= productRepository.save(product);
           return new ObjectResponse<Product>(ResponeStatus.SUCCESS,"",result);
       }   catch (Exception e){
           return   new ObjectResponse<Product>(ResponeStatus.ERROR,e.toString(),null);
       }
    }

    public ObjectResponse<Product> updateProduct(Product product){
                try {
                    Product result = productRepository.save(product);
                    return new ObjectResponse<Product>(ResponeStatus.SUCCESS, "", result);
                } catch (Exception e) {
                    return new ObjectResponse<Product>(ResponeStatus.ERROR, e.toString(), null);
                }
            }
}
