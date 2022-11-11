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

    public boolean isNull(){
        return id == null && name == null && age == null && gender == null && salary == null;
    }

}
