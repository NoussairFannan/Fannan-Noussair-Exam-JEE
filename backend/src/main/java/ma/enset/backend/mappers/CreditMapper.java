package ma.enset.backend.mappers;

import ma.enset.backend.dtos.*;
import ma.enset.backend.entities.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CreditMapper {

    public CreditDTO fromCredit(Credit credit) {
        CreditDTO creditDTO;
        
        if (credit instanceof PersonalCredit) {
            PersonalCredit personalCredit = (PersonalCredit) credit;
            creditDTO = new PersonalCreditDTO(personalCredit.getPurpose());
        } else if (credit instanceof ProfessionalCredit) {
            ProfessionalCredit professionalCredit = (ProfessionalCredit) credit;
            creditDTO = new ProfessionalCreditDTO(professionalCredit.getPurpose(), professionalCredit.getCompanyName());
        } else if (credit instanceof RealEstateCredit) {
            RealEstateCredit realEstateCredit = (RealEstateCredit) credit;
            creditDTO = new RealEstateCreditDTO(realEstateCredit.getPropertyType());
        } else {
            creditDTO = new CreditDTO();
        }
        
        creditDTO.setId(credit.getId());
        creditDTO.setRequestDate(credit.getRequestDate());
        creditDTO.setStatus(credit.getStatus());
        creditDTO.setAcceptanceDate(credit.getAcceptanceDate());
        creditDTO.setAmount(credit.getAmount());
        creditDTO.setRepaymentDuration(credit.getRepaymentDuration());
        creditDTO.setInterestRate(credit.getInterestRate());
        
        if (credit.getClient() != null) {
            creditDTO.setClientId(credit.getClient().getId());
        }
        
        if (credit.getRepayments() != null) {
            creditDTO.setRepaymentIds(
                credit.getRepayments().stream()
                    .map(Repayment::getId)
                    .collect(Collectors.toList())
            );
        }
        
        return creditDTO;
    }
    
    public Credit toCredit(CreditDTO creditDTO) {
        Credit credit;
        
        if (creditDTO instanceof PersonalCreditDTO) {
            PersonalCreditDTO personalCreditDTO = (PersonalCreditDTO) creditDTO;
            PersonalCredit personalCredit = new PersonalCredit();
            personalCredit.setPurpose(personalCreditDTO.getPurpose());
            credit = personalCredit;
        } else if (creditDTO instanceof ProfessionalCreditDTO) {
            ProfessionalCreditDTO professionalCreditDTO = (ProfessionalCreditDTO) creditDTO;
            ProfessionalCredit professionalCredit = new ProfessionalCredit();
            professionalCredit.setPurpose(professionalCreditDTO.getPurpose());
            professionalCredit.setCompanyName(professionalCreditDTO.getCompanyName());
            credit = professionalCredit;
        } else if (creditDTO instanceof RealEstateCreditDTO) {
            RealEstateCreditDTO realEstateCreditDTO = (RealEstateCreditDTO) creditDTO;
            RealEstateCredit realEstateCredit = new RealEstateCredit();
            realEstateCredit.setPropertyType(realEstateCreditDTO.getPropertyType());
            credit = realEstateCredit;
        } else {
            throw new IllegalArgumentException("Unknown credit type: " + creditDTO.getClass().getName());
        }
        
        credit.setId(creditDTO.getId());
        credit.setRequestDate(creditDTO.getRequestDate());
        credit.setStatus(creditDTO.getStatus());
        credit.setAcceptanceDate(creditDTO.getAcceptanceDate());
        credit.setAmount(creditDTO.getAmount());
        credit.setRepaymentDuration(creditDTO.getRepaymentDuration());
        credit.setInterestRate(creditDTO.getInterestRate());
        

        return credit;
    }
}