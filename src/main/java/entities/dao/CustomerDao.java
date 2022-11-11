package entities.dao;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CustomerDao {
    private Integer id;
    private String name;
    private String email;
}
