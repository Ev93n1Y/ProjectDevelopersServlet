package service;

import entities.dao.CustomerDao;
import entities.dao.DeveloperDao;
import entities.dto.CustomerDto;
import entities.dto.DeveloperDto;
import repository.CustomerRepository;
import service.converter.CustomerConverter;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements Crud<CustomerDto> {
    private final CustomerRepository repository;
    private final CustomerConverter converter;

    public CustomerService(CustomerRepository repository, CustomerConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public CustomerDto create(CustomerDto dto) {
        CustomerDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<CustomerDto> read(Integer id) {
        List<CustomerDao> daoList = repository.selectById(id);
        List<CustomerDto> dtoList = new ArrayList<>();
        for(CustomerDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<CustomerDto> read(String name) {
        List<CustomerDao> daoList = repository.selectByDepartment(name);
        List<CustomerDto> dtoList = new ArrayList<>();
        for(CustomerDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public CustomerDto update(Integer id, CustomerDto dto) {
        CustomerDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
