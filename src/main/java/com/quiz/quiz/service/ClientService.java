package com.quiz.quiz.service;

import com.quiz.quiz.dto.ListResponse;
import com.quiz.quiz.dto.ObjectResponse;
import com.quiz.quiz.entity.Client;
import com.quiz.quiz.repository.ClientRepository;
import com.quiz.quiz.utils.ResponeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public  ListResponse<Client>  getAllClients(int pageSize,int pageNumber){
        try{
        Sort sort = Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.asc("lastName"));
        if (pageSize==-1){
          List<Client> data=  clientRepository.findAll(sort);
            ListResponse<Client> response=new ListResponse<Client>(ResponeStatus.SUCCESS,"",data.size(),1,0,data.size(),data);
            return response;
        }else{
            Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
          Page page = clientRepository.findAll(paging);

            ListResponse<Client> response=new ListResponse<Client>(ResponeStatus.SUCCESS,"",page.getTotalElements(),page.getTotalPages(),pageNumber,pageSize,page.getContent());

           return response;
        }
        } catch (Exception e){
            return   new ListResponse<Client>(ResponeStatus.ERROR,e.toString());
        }


    }

    public ObjectResponse<Client> createClient(Client client){
        try{
       Client res= clientRepository.save(client);
            return   new ObjectResponse<Client>(ResponeStatus.SUCCESS,"",client);
        } catch (Exception e){
        return   new ObjectResponse<Client>(ResponeStatus.ERROR,e.toString(),null);
    }
    }

    public ObjectResponse<Client> updateClient(Map<String,Object> updates, int id) {
        try {
            Client client = clientRepository.findById(id).get();


            updates.forEach(
                    (change, value) -> {
                        switch (change) {
                            case "name":
                                client.setName((String) value);
                                break;
                            case "lastName":
                                client.setLastName((String) value);
                                break;
                            case "mobile":
                                client.setMobile((String) value);
                                break;
                        }
                    }
            );

            Client result= clientRepository.save(client);
            return  new ObjectResponse<Client>(ResponeStatus.SUCCESS,"",result);
        }catch (Exception e){
            return   new ObjectResponse<Client>(ResponeStatus.ERROR,e.toString(),null);
        }
    }
}
