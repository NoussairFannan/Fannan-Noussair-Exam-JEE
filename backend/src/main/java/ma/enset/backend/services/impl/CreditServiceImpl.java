package ma.enset.backend.services.impl;

import ma.enset.backend.dtos.CreditDTO;
import ma.enset.backend.dtos.PersonalCreditDTO;
import ma.enset.backend.dtos.ProfessionalCreditDTO;
import ma.enset.backend.dtos.RealEstateCreditDTO;
import ma.enset.backend.entities.Client;
import ma.enset.backend.entities.Credit;
import ma.enset.backend.enums.CreditStatus;
import ma.enset.backend.mappers.CreditMapper;
import ma.enset.backend.repositories.ClientRepository;
import ma.enset.backend.repositories.CreditRepository;
import ma.enset.backend.services.CreditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final CreditMapper creditMapper;

    public CreditServiceImpl(CreditRepository creditRepository, ClientRepository clientRepository, CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.clientRepository = clientRepository;
        this.creditMapper = creditMapper;
    }

    @Override
    public CreditDTO getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + id));
        return creditMapper.fromCredit(credit);
    }

    @Override
    public List<CreditDTO> getAllCredits() {
        List<Credit> credits = creditRepository.findAll();
        return credits.stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        Credit credit = creditMapper.toCredit(creditDTO);
        
        // Set client if clientId is provided
        if (creditDTO.getClientId() != null) {
            Client client = clientRepository.findById(creditDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + creditDTO.getClientId()));
            credit.setClient(client);
        }
        
        // Set default values if not provided
        if (credit.getRequestDate() == null) {
            credit.setRequestDate(new Date());
        }
        if (credit.getStatus() == null) {
            credit.setStatus(CreditStatus.IN_PROGRESS);
        }
        
        Credit savedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO updateCredit(CreditDTO creditDTO) {
        // Check if credit exists
        creditRepository.findById(creditDTO.getId())
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditDTO.getId()));
        
        Credit credit = creditMapper.toCredit(creditDTO);
        
        // Set client if clientId is provided
        if (creditDTO.getClientId() != null) {
            Client client = clientRepository.findById(creditDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + creditDTO.getClientId()));
            credit.setClient(client);
        }
        
        Credit updatedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(updatedCredit);
    }

    @Override
    public void deleteCredit(Long id) {
        // Check if credit exists
        creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + id));
        
        creditRepository.deleteById(id);
    }

    @Override
    public PersonalCreditDTO savePersonalCredit(PersonalCreditDTO personalCreditDTO) {
        Credit credit = creditMapper.toCredit(personalCreditDTO);
        
        // Set client if clientId is provided
        if (personalCreditDTO.getClientId() != null) {
            Client client = clientRepository.findById(personalCreditDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + personalCreditDTO.getClientId()));
            credit.setClient(client);
        }
        
        // Set default values if not provided
        if (credit.getRequestDate() == null) {
            credit.setRequestDate(new Date());
        }
        if (credit.getStatus() == null) {
            credit.setStatus(CreditStatus.IN_PROGRESS);
        }
        
        Credit savedCredit = creditRepository.save(credit);
        return (PersonalCreditDTO) creditMapper.fromCredit(savedCredit);
    }

    @Override
    public ProfessionalCreditDTO saveProfessionalCredit(ProfessionalCreditDTO professionalCreditDTO) {
        Credit credit = creditMapper.toCredit(professionalCreditDTO);
        
        // Set client if clientId is provided
        if (professionalCreditDTO.getClientId() != null) {
            Client client = clientRepository.findById(professionalCreditDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + professionalCreditDTO.getClientId()));
            credit.setClient(client);
        }
        
        // Set default values if not provided
        if (credit.getRequestDate() == null) {
            credit.setRequestDate(new Date());
        }
        if (credit.getStatus() == null) {
            credit.setStatus(CreditStatus.IN_PROGRESS);
        }
        
        Credit savedCredit = creditRepository.save(credit);
        return (ProfessionalCreditDTO) creditMapper.fromCredit(savedCredit);
    }

    @Override
    public RealEstateCreditDTO saveRealEstateCredit(RealEstateCreditDTO realEstateCreditDTO) {
        Credit credit = creditMapper.toCredit(realEstateCreditDTO);
        
        // Set client if clientId is provided
        if (realEstateCreditDTO.getClientId() != null) {
            Client client = clientRepository.findById(realEstateCreditDTO.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + realEstateCreditDTO.getClientId()));
            credit.setClient(client);
        }
        
        // Set default values if not provided
        if (credit.getRequestDate() == null) {
            credit.setRequestDate(new Date());
        }
        if (credit.getStatus() == null) {
            credit.setStatus(CreditStatus.IN_PROGRESS);
        }
        
        Credit savedCredit = creditRepository.save(credit);
        return (RealEstateCreditDTO) creditMapper.fromCredit(savedCredit);
    }

    @Override
    public List<CreditDTO> getCreditsByClientId(Long clientId) {
        // Check if client exists
        clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        
        List<Credit> credits = creditRepository.findAll().stream()
                .filter(credit -> credit.getClient() != null && credit.getClient().getId().equals(clientId))
                .collect(Collectors.toList());
        
        return credits.stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByStatus(CreditStatus status) {
        List<Credit> credits = creditRepository.findAll().stream()
                .filter(credit -> credit.getStatus() == status)
                .collect(Collectors.toList());
        
        return credits.stream()
                .map(creditMapper::fromCredit)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO updateCreditStatus(Long creditId, CreditStatus status) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditId));
        
        credit.setStatus(status);
        
        // If status is ACCEPTED, set acceptance date
        if (status == CreditStatus.ACCEPTED) {
            credit.setAcceptanceDate(new Date());
        }
        
        Credit updatedCredit = creditRepository.save(credit);
        return creditMapper.fromCredit(updatedCredit);
    }

    @Override
    public double calculateTotalCreditAmount() {
        return creditRepository.findAll().stream()
                .mapToDouble(Credit::getAmount)
                .sum();
    }

    @Override
    public double calculateMonthlyPayment(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditId));
        
        // Simple monthly payment calculation (principal + interest) / duration in months
        double principal = credit.getAmount();
        double monthlyInterestRate = credit.getInterestRate() / 100 / 12; // Convert annual rate to monthly
        int durationInMonths = credit.getRepaymentDuration();
        
        // Using the formula for fixed monthly payments (amortization)
        // M = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        // where M is the monthly payment, P is the principal, r is the monthly interest rate, and n is the number of months
        
        double numerator = monthlyInterestRate * Math.pow(1 + monthlyInterestRate, durationInMonths);
        double denominator = Math.pow(1 + monthlyInterestRate, durationInMonths) - 1;
        
        return principal * (numerator / denominator);
    }

    @Override
    public double calculateTotalInterest(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("Credit not found with id: " + creditId));
        
        // Calculate total interest as (monthly payment * duration in months) - principal
        double monthlyPayment = calculateMonthlyPayment(creditId);
        int durationInMonths = credit.getRepaymentDuration();
        double principal = credit.getAmount();
        
        return (monthlyPayment * durationInMonths) - principal;
    }
}