package service;

import entities.dao.CompanyDao;
import entities.dao.CustomerDao;
import entities.dto.CompanyDto;
import entities.dto.CustomerDto;
import repository.CompanyRepository;
import service.converter.CompanyConverter;

import java.util.ArrayList;
import java.util.List;

public class CompanyService implements Crud<CompanyDto> {
    private final CompanyRepository repository;
    private final CompanyConverter converter;

    public CompanyService(CompanyRepository repository, CompanyConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public CompanyDto create(CompanyDto dto) {
        CompanyDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<CompanyDto> read(Integer id) {
        List<CompanyDao> daoList = repository.selectById(id);
        List<CompanyDto> dtoList = new ArrayList<>();
        for(CompanyDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<CompanyDto> read(String name) {
        List<CompanyDao> daoList = repository.selectByDepartment(name);
        List<CompanyDto> dtoList = new ArrayList<>();
        for(CompanyDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public CompanyDto update(Integer id, CompanyDto dto) {
        CompanyDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }


}
