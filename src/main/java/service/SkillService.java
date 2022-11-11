package service;

import entities.dao.SkillDao;
import entities.dto.SkillDto;
import repository.SkillRepository;
import service.converter.SkillConverter;

import java.sql.SQLException;

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
    public SkillDto read(Integer id) {
        SkillDao dao = repository.selectById(id);
        return converter.from(dao);
    }

    @Override
    public SkillDto read(String name) {
        SkillDao dao = repository.selectByName(name);
        return converter.from(dao);
    }

    @Override
    public SkillDto update(Integer id, SkillDto dto) {
        SkillDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        repository.deleteById(id);
    }
}
