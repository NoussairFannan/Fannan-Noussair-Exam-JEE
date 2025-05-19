package ma.enset.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentDTO {
    private Long id;
    private Date repaymentDate;
    private Double amount;
    private Long creditId;
}