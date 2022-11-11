package entities.dao;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class DeveloperDao {
    Integer id;
    String name;
    Integer age;
    String gender;
    Integer salary;
}
