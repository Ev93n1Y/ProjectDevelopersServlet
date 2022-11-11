package entities.dao;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class SkillDao {
    private Integer id;
    private String department;
    private String level;
}
