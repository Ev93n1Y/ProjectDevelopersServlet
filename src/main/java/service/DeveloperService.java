package service;

import entities.dao.DeveloperDao;
import entities.dao.ProjectDao;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import repository.DeveloperRepository;
import service.converter.DeveloperConverter;

import java.util.ArrayList;
import java.util.List;

public class DeveloperService implements Crud<DeveloperDto> {
    private final DeveloperRepository repository;
    private final DeveloperConverter converter;

    public DeveloperService(DeveloperRepository repository, DeveloperConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public DeveloperDto create(DeveloperDto dto) {
        DeveloperDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<DeveloperDto> read(Integer id) {
        List<DeveloperDao> daoList = repository.selectById(id);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for(DeveloperDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<DeveloperDto> read(String name) {
        List<DeveloperDao> daoList = repository.selectByDepartment(name);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for(DeveloperDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public DeveloperDto update(Integer id, DeveloperDto dto) {
        DeveloperDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Integer totalDevelopersSalaryByProject(Integer id) {
        return repository.selectTotalSalaryByProjectId(id);
    }

    public List<DeveloperDto> allDevelopersByProject(Integer id) {
        List<DeveloperDao> daoList = repository.selectAllDevelopersByProject(id);
        List<DeveloperDto> dtoList = new ArrayList<>();
        for(DeveloperDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public List<DeveloperDto> allJavaDevelopers() {
        List<DeveloperDao> daoList = repository.selectAllJavaDevelopers();
        List<DeveloperDto> dtoList = new ArrayList<>();
        for(DeveloperDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    public List<DeveloperDto> allMiddleDevelopers() {
        List<DeveloperDao> daoList = repository.selectAllMiddleDevelopers();
        List<DeveloperDto> dtoList = new ArrayList<>();
        for(DeveloperDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }
}
