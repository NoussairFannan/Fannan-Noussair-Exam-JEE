package ma.enset.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.backend.enums.CreditStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private Date requestDate;
    private CreditStatus status;
    private Date acceptanceDate;
    private Double amount;
    private Integer repaymentDuration;
    private Double interestRate;
    private Long clientId;
    private List<Long> repaymentIds = new ArrayList<>();
    private String type; // PersonalCredit, ProfessionalCredit, RealEstateCredit
}