package service.converter;

import entities.dao.CustomerDao;
import entities.dto.CustomerDto;

public class CustomerConverter implements Convertable<CustomerDto, CustomerDao>{
    @Override
    public CustomerDto from(CustomerDao entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    @Override
    public CustomerDao to(CustomerDto entity) {
        CustomerDao dao = new CustomerDao();
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setEmail(entity.getEmail());
        return dao;
    }
}
