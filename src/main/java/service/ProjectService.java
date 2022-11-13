package service;

import entities.dao.ProjectDao;
import entities.dao.SkillDao;
import entities.dto.ProjectDto;
import entities.dto.SkillDto;
import repository.ProjectRepository;
import service.converter.ProjectConverter;

import java.util.ArrayList;
import java.util.List;

public class ProjectService implements Crud<ProjectDto> {
    private final ProjectRepository repository;
    private final ProjectConverter converter;

    public ProjectService(ProjectRepository repository, ProjectConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public ProjectDto create(ProjectDto dto) {
        ProjectDao dao = repository.save(converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public List<ProjectDto> read(Integer id) {
        List<ProjectDao> daoList = repository.selectById(id);
        List<ProjectDto> dtoList = new ArrayList<>();
        for(ProjectDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public List<ProjectDto> read(String name) {
        List<ProjectDao> daoList = repository.selectByDepartment(name);
        List<ProjectDto> dtoList = new ArrayList<>();
        for(ProjectDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }

    @Override
    public ProjectDto update(Integer id, ProjectDto dto) {
        ProjectDao dao = repository.updateById(id, converter.to(dto));
        return converter.from(dao);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<ProjectDto> allProjects() {
        List<ProjectDao> daoList = repository.selectAllProjects();
        List<ProjectDto> dtoList = new ArrayList<>();
        for(ProjectDao dao:daoList){
            dtoList.add(converter.from(dao));
        }
        return dtoList;
    }
}
