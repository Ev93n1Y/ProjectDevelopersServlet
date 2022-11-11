package entities.dto;

import lombok.*;
import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class ProjectDto {
    private Integer id;
    private String name;
    private Integer company_id;
    private Integer customer_id;
    private Integer cost;
    private Date creation_date;

    public boolean isNull(){
        return id == null && name == null && company_id == null
                && customer_id == null && cost == null && creation_date == null;
    }
}
