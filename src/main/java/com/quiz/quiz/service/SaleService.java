package com.quiz.quiz.service;

import com.quiz.quiz.dto.*;
import com.quiz.quiz.entity.Client;
import com.quiz.quiz.entity.Product;
import com.quiz.quiz.entity.Sale;
import com.quiz.quiz.entity.Transaction;
import com.quiz.quiz.repository.ClientRepository;
import com.quiz.quiz.repository.ProductRepository;
import com.quiz.quiz.repository.SaleRepository;
import com.quiz.quiz.repository.TransactionRepository;
import com.quiz.quiz.utils.ResponeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleService {
    static final Logger log =
            LoggerFactory.getLogger(SaleService.class);
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public  ListResponse<Sale>  getAllSales(int pageSize,int pageNumber){
        try {
            Sort sort = Sort.by(
                    Sort.Order.asc("creationDate"));
            if (pageSize == -1) {
                List<Sale> data = saleRepository.findAll(sort);
                ListResponse<Sale> response = new ListResponse<Sale>(ResponeStatus.SUCCESS,"",data.size(), 1, 0, data.size(), data);
                return response;
            } else {
                Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
                Page page = saleRepository.findAll(paging);
                List<Sale> sales = page.getContent();

                ListResponse<Sale> response = new ListResponse<Sale>(ResponeStatus.SUCCESS,"",page.getTotalElements(), page.getTotalPages(), pageNumber, pageSize, sales);
                log.info("some str");
                return response;
            }
        }   catch (Exception e){
            return   new ListResponse<Sale>(ResponeStatus.ERROR,e.toString());
        }
    }
    @Transactional
    public ObjectResponse<SaleResponseDto> createSale(SaleRequestDto saleDto){
        try {
            Sale sale = new Sale();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

                sale.setCreationDate(dtf.format(LocalDateTime.now()));

            Client client = clientRepository.findById(saleDto.getClientId()).get();

            sale. setSeller(saleDto.getSeller());
                double total = saleDto.getTransactions().stream().mapToDouble(trans -> trans.getCount() * trans.getProductPrice()).sum();

            sale.setTotal(total);
            sale.setClient(client);

            Sale res= saleRepository.save(sale);

            List<Transaction> transactions=new ArrayList<Transaction>();
            List<TransActionResponseDto> transactionsToSend=new ArrayList<TransActionResponseDto>();
            saleDto.getTransactions().forEach(el->{
            Transaction trans= new Transaction();
               trans. setCount(el.getCount());
              trans.  setProduct(productRepository.findById(el.getProductId()).get());
                 trans.setSale(res);

                transactions.add(trans);
                TransActionResponseDto transToSend= new TransActionResponseDto(trans.getCount(),trans.getProduct());
                transactionsToSend.add(transToSend);

            });
            List<Transaction> oldTransactions=transactionRepository.findBySaleId(sale.getId());
           transactionRepository.saveAll(transactions);
            SaleResponseDto response=new SaleResponseDto(res,transactionsToSend,client);
            return new ObjectResponse<SaleResponseDto>(ResponeStatus.SUCCESS,"",response);
        }catch (Exception e){
            return new ObjectResponse<SaleResponseDto>(ResponeStatus.ERROR,e.toString(),null);
        }
    }

    @Transactional
    public ObjectResponse<SaleResponseDto> updateSale(SaleRequestDto saleDto){
        try {
            if (!saleRepository.existsById(saleDto.getId())){
                throw new Exception("no such entry");
            }
            Sale sale = new Sale();

                sale.setId(saleDto.getId());

            Client client = clientRepository.findById(saleDto.getClientId()).get();

            sale. setSeller(saleDto.getSeller());
            double total = saleDto.getTransactions().stream().mapToDouble(trans -> trans.getCount() * trans.getProductPrice()).sum();

            sale.setTotal(total);
            sale.setClient(client);

            Sale res= saleRepository.save(sale);

            List<Transaction> transactions=new ArrayList<Transaction>();
            List<TransActionResponseDto> transactionsToSend=new ArrayList<TransActionResponseDto>();
            saleDto.getTransactions().forEach(el->{
                Transaction trans= new Transaction();
                trans. setCount(el.getCount());
                trans.  setProduct(productRepository.findById(el.getProductId()).get());
                trans.setSale(res);

                    trans.setId(el.getId());

                transactions.add(trans);
                TransActionResponseDto transToSend= new TransActionResponseDto(trans.getCount(),trans.getProduct());
                transactionsToSend.add(transToSend);

            });
            List<Transaction> oldTransactions=transactionRepository.findBySaleId(sale.getId());
            logTransactionsChanges(oldTransactions,transactions);

                transactionRepository.deleteBySaleId(saleDto.getId());

            transactionRepository.saveAll(transactions);
            SaleResponseDto response=new SaleResponseDto(res,transactionsToSend,client);
            return new ObjectResponse<SaleResponseDto>(ResponeStatus.SUCCESS,"",response);
        }catch (Exception e){
            return new ObjectResponse<SaleResponseDto>(ResponeStatus.ERROR,e.toString(),null);
        }
    }
    void logTransactionsChanges(List<Transaction> oldTransactions,List<Transaction> newTransactions){
        oldTransactions.forEach(el->{
           List<Transaction> res=newTransactions.stream().filter(newTrans->newTrans.getId()==el.getId()).collect(Collectors.toList());
           if (res.stream().count()==0){
               log.info("delete Item:"+el.toString());
           }else{
               String compareResult=el.compare(res.get(0));
              if (!compareResult.equals("")){
                  log.info("edit Item:"+el.getId()+" "+compareResult);
              }
           }

        });
       List<Transaction> newItems= newTransactions.stream().
                filter(el->oldTransactions.stream().noneMatch(newTrans->newTrans.getId()==el.getId())).collect(Collectors.toList());
        newItems.forEach(el->{
          log.info("new Item:"+el.toString());
        });
    }
}
