package entities.dao;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CompanyDao {
    private Integer id;
    private String name;
    private String location;
}
