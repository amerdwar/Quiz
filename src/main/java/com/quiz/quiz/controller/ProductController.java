package com.quiz.quiz.controller;


import com.quiz.quiz.dto.ListResponse;
import com.quiz.quiz.dto.ObjectResponse;
import com.quiz.quiz.entity.Product;
import com.quiz.quiz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("Product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity <?>getAllProducts(@RequestParam(defaultValue = "-1") int pageSize
            , @RequestParam(defaultValue = "0")  int pageNumber ){
        //System.out.println(itemMaxNumber+"");
        return ResponseEntity.ok(productService.getAllProducts(pageSize,pageNumber));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        return ResponseEntity.ok( productService.updateProduct(product));
    }


}
