package ma.enset.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalCreditDTO extends CreditDTO {
    private String purpose;
    private String companyName;
    
//    // Constructor to set the type field
//    public ProfessionalCreditDTO(String purpose, String companyName) {
//        this.purpose = purpose;
//        this.companyName = companyName;
//        setType("ProfessionalCredit");
//    }
}