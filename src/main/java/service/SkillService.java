package service;

import entities.dao.DeveloperDao;
import entities.dao.SkillDao;
import entities.dto.DeveloperDto;
import entities.dto.SkillDto;
import repository.SkillRepository;
import service.converter.SkillConverter;

import java.util.ArrayList;
import java.util.List;

public class SkillService implements Crud<SkillDto> {
    private final SkillRepository repository;
    private final SkillConverter converter;

    public SkillService(SkillRepository repository, SkillConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public SkillDto create(SkillDto dto) {
        SkillDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<SkillDto> read(Integer id) {
        List<SkillDao> daoList = repository.selectById(id);
        List<SkillDto> dtoList = new ArrayList<>();
        for(SkillDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<SkillDto> read(String department) {
        List<SkillDao> daoList = repository.selectByDepartment(department);
        List<SkillDto> dtoList = new ArrayList<>();
        for(SkillDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public SkillDto update(Integer id, SkillDto dto) {
        SkillDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
