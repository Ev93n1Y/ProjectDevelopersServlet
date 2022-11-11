package entities.dao;

import lombok.*;
import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class ProjectDao {
    private Integer id;
    private String name;
    private Integer company_id;
    private Integer customer_id;
    private Integer cost;
    private Date creation_date;
}
