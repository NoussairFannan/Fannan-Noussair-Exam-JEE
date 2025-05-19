package ma.enset.backend.mappers;

import ma.enset.backend.dtos.RepaymentDTO;
import ma.enset.backend.entities.Repayment;
import org.springframework.stereotype.Component;

@Component
public class RepaymentMapper {

    public RepaymentDTO fromRepayment(Repayment repayment) {
        RepaymentDTO repaymentDTO = new RepaymentDTO();
        repaymentDTO.setId(repayment.getId());
        repaymentDTO.setRepaymentDate(repayment.getRepaymentDate());
        repaymentDTO.setAmount(repayment.getAmount());

        if (repayment.getCredit() != null) {
            repaymentDTO.setCreditId(repayment.getCredit().getId());
        }
        
        return repaymentDTO;
    }
    
    public Repayment toRepayment(RepaymentDTO repaymentDTO) {
        Repayment repayment = new Repayment();
        repayment.setId(repaymentDTO.getId());
        repayment.setRepaymentDate(repaymentDTO.getRepaymentDate());
        repayment.setAmount(repaymentDTO.getAmount());

        return repayment;
    }
}