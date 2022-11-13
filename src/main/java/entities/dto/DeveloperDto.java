package entities.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class DeveloperDto {
    Integer id;
    String name;
    Integer age;
    String gender;
    Integer salary;
}
