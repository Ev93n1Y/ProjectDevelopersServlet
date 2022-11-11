package entities.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class SkillDto {
    private Integer id;
    private String department;
    private String level;

    public boolean isNull(){
        return id == null && department == null && level == null;
    }
}
