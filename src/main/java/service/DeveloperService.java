package service;

import entities.dao.DeveloperDao;
import entities.dto.DeveloperDto;
import repository.DeveloperRepository;
import service.converter.DeveloperConverter;

import java.sql.SQLException;
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
    public DeveloperDto read(Integer id) {
        DeveloperDao dao = repository.selectById(id);
        return converter.from(dao);
    }

    @Override
    public DeveloperDto read(String name) {
        DeveloperDao dao = repository.selectByName(name);
        return converter.from(dao);
    }

    @Override
    public DeveloperDto update(Integer id, DeveloperDto dto) {
        DeveloperDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        repository.deleteById(id);
    }

    public Integer totalDevelopersSalaryByProject(Integer id) {
        return repository.selectTotalSalaryByProjectId(id);
    }

    public List<DeveloperDao> allDevelopersByProject(Integer id) {
        return repository.selectAllDevelopersByProjectId(id);
    }

    public StringBuilder allJavaDevelopers() {
        return repository.selectAllJavaDevelopers();
    }

    public StringBuilder allMiddleDevelopers() {
        return repository.selectAllMiddleDevelopers();
    }
}
