package service.converter;

import entities.dao.ProjectDao;
import entities.dto.ProjectDto;

public class ProjectConverter implements Convertable<ProjectDto, ProjectDao>{
    @Override
    public ProjectDto from(ProjectDao entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCompany_id(entity.getCompany_id());
        dto.setCustomer_id(entity.getCustomer_id());
        dto.setCost(entity.getCost());
        dto.setCreation_date(entity.getCreation_date());
        return dto;
    }

    @Override
    public ProjectDao to(ProjectDto entity) {
        ProjectDao dao = new ProjectDao();
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setCompany_id(entity.getCompany_id());
        dao.setCustomer_id(entity.getCustomer_id());
        dao.setCost(entity.getCost());
        dao.setCreation_date(entity.getCreation_date());
        return dao;
    }
}
