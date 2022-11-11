package service.converter;

import entities.dao.CompanyDao;
import entities.dto.CompanyDto;

public class CompanyConverter implements Convertable<CompanyDto, CompanyDao> {
    @Override
    public CompanyDto from(CompanyDao entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        return dto;
    }

    @Override
    public CompanyDao to(CompanyDto entity) {
        CompanyDao dao = new CompanyDao();
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setLocation(entity.getLocation());
        return dao;
    }
}
