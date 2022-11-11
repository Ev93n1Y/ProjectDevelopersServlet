package entities.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CompanyDto {
    private Integer id;
    private String name;
    private String location;

    public boolean isNull(){
        return id == null && name == null && location == null;
    }
}
