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
}
