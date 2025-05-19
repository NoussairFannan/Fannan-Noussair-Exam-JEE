package ma.enset.backend.services.impl;

import ma.enset.backend.dtos.RepaymentDTO;
import ma.enset.backend.entities.Credit;
import ma.enset.backend.entities.Repayment;
import ma.enset.backend.mappers.RepaymentMapper;
import ma.enset.backend.repositories.CreditRepository;
import ma.enset.backend.repositories.RepaymentRepository;
import ma.enset.backend.services.RepaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepaymentServiceImpl implements RepaymentService {
    private final RepaymentRepository repaymentRepository;
    private final CreditRepository creditRepository;
    private final RepaymentMapper repaymentMapper;

    public RepaymentServiceImpl(RepaymentRepository repaymentRepository, CreditRepository creditRepository, RepaymentMapper repaymentMapper) {
        this.repaymentRepository = repaymentRepository;
        this.creditRepository = creditRepository;
        this.repaymentMapper = repaymentMapper;
    }

    @Override
    public RepaymentDTO getRepaymentById(Long id) {
        Repayment repayment = repaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repayment not found with id: " + id));
        return repaymentMapper.fromRepayment(repayment);
    }

    @Override
    public List<RepaymentDTO> getAllRepayments() {
        List<Repayment> repayments = repaymentRepository.findAll();
        return repayments.stream()
                .map(repaymentMapper::fromRepayment)
                .collect(Collectors.toList());
    }

    @Override
    public RepaymentDTO saveRepayment(RepaymentDTO repaymentDTO) {
        Repayment repayment = repaymentMapper.toRepayment(repaymentDTO);
        
        if (repaymentDTO.getCreditId() != null) {
            Credit credit = creditRepository.findById(repaymentDTO.getCreditId())
                    .orElseThrow(() -> new RuntimeException("Credit not found with id: " + repaymentDTO.getCreditId()));
            repayment.setCredit(credit);
        }
        
        if (repayment.getRepaymentDate() == null) {
            repayment.setRepaymentDate(new Date());
        }
        
        Repayment savedRepayment = repaymentRepository.save(repayment);
        return repaymentMapper.fromRepayment(savedRepayment);
    }

    @Override
    public RepaymentDTO updateRepayment(RepaymentDTO repaymentDTO) {
        repaymentRepository.findById(repaymentDTO.getId())
                .orElseThrow(() -> new RuntimeException("Repayment not found with id: " + repaymentDTO.getId()));
        
        Repayment repayment = repaymentMapper.toRepayment(repaymentDTO);
        
        if (repaymentDTO.getCreditId() != null) {
            Credit credit = creditRepository.findById(repaymentDTO.getCreditId())
                    .orElseThrow(() -> new RuntimeException("Credit not found with id: " + repaymentDTO.getCreditId()));
            repayment.setCredit(credit);
        }
        
        Repayment updatedRepayment = repaymentRepository.save(repayment);
        return repaymentMapper.fromRepayment(updatedRepayment);
    }

    @Override
    public void deleteRepayment(Long id) {
        repaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repayment not found with id: " + id));
        
        repaymentRepository.deleteById(id);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<RepaymentDTO> getRepaymentsByCreditId(Long creditId) {
        creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditId));
        
        List<Repayment> repayments = repaymentRepository.findAll().stream()
                .filter(repayment -> repayment.getCredit() != null && repayment.getCredit().getId().equals(creditId))
                .collect(Collectors.toList());
        
        return repayments.stream()
                .map(repaymentMapper::fromRepayment)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateTotalRepaymentAmount(Long creditId) {
        creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditId));
        
        return repaymentRepository.findAll().stream()
                .filter(repayment -> repayment.getCredit() != null && repayment.getCredit().getId().equals(creditId))
                .mapToDouble(Repayment::getAmount)
                .sum();
    }

}