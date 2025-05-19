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
    List<RepaymentDTO> getRepaymentsByDateRange(Date startDate, Date endDate);
    double calculateTotalRepaymentAmount(Long creditId);
    double calculateRemainingAmount(Long creditId);
}