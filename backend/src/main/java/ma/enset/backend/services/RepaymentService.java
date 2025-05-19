package ma.enset.backend.services;

import ma.enset.backend.dtos.RepaymentDTO;

import java.util.Date;
import java.util.List;

public interface RepaymentService {
    // CRUD operations
    RepaymentDTO getRepaymentById(Long id);
    List<RepaymentDTO> getAllRepayments();
    RepaymentDTO saveRepayment(RepaymentDTO repaymentDTO);
    RepaymentDTO updateRepayment(RepaymentDTO repaymentDTO);
    void deleteRepayment(Long id);

    // Additional business operations
    List<RepaymentDTO> getRepaymentsByCreditId(Long creditId);
    double calculateTotalRepaymentAmount(Long creditId);
}
