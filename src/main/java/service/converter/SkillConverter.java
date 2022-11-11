package service.converter;

import entities.dao.SkillDao;
import entities.dto.SkillDto;

public class SkillConverter implements Convertable<SkillDto, SkillDao>{
    @Override
    public SkillDto from(SkillDao entity) {
        SkillDto dto = new SkillDto();
        dto.setId(entity.getId());
        dto.setDepartment(entity.getDepartment());
        dto.setLevel(entity.getLevel());
        return dto;
    }

    @Override
    public SkillDao to(SkillDto entity) {
        SkillDao dao = new SkillDao();
        dao.setId(entity.getId());
        dao.setDepartment(entity.getDepartment());
        dao.setLevel(entity.getLevel());
        return dao;
    }
}
