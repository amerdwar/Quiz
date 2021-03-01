package com.quiz.quiz.controller;


import com.quiz.quiz.dto.ListResponse;
import com.quiz.quiz.dto.SaleRequestDto;
import com.quiz.quiz.dto.SaleResponseDto;
import com.quiz.quiz.entity.Sale;
import com.quiz.quiz.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("Sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping("/createSale")
    public ResponseEntity<?>  createSale(@RequestBody SaleRequestDto sale){
        return ResponseEntity.ok(saleService.createSale(sale));
    }


    @GetMapping("/getAllSales")
    public ResponseEntity<?> getAllSales(@RequestParam(defaultValue = "-1") int pageSize
            , @RequestParam(defaultValue = "0")  int pageNumber ){
        //System.out.println(itemMaxNumber+"");
        ListResponse<Sale> sale= saleService.getAllSales(pageSize,pageNumber);
        return ResponseEntity.ok(sale);
    }

    @PutMapping("/updateSale")
    public ResponseEntity<?>  updateSale(@RequestBody SaleRequestDto sale){

        return ResponseEntity.ok(saleService.updateSale(sale));
    }


}
