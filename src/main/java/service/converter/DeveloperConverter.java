package service.converter;

import entities.dao.DeveloperDao;
import entities.dto.DeveloperDto;

public class DeveloperConverter implements Convertable<DeveloperDto, DeveloperDao>{
    @Override
    public DeveloperDto from(DeveloperDao entity) {
        DeveloperDto dto = new DeveloperDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setSalary(entity.getSalary());
        return dto;
    }

    @Override
    public DeveloperDao to(DeveloperDto entity) {
        DeveloperDao dao = new DeveloperDao();
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setAge(entity.getAge());
        dao.setGender(entity.getGender());
        dao.setSalary(entity.getSalary());
        return dao;
    }
}
