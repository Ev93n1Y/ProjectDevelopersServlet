package entities.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;

    public boolean isNull(){
        return id == null && name == null && email == null;
    }
}
