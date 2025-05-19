package ma.enset.backend.services;

import ma.enset.backend.dtos.CreditDTO;
import ma.enset.backend.dtos.PersonalCreditDTO;
import ma.enset.backend.dtos.ProfessionalCreditDTO;
import ma.enset.backend.dtos.RealEstateCreditDTO;
import ma.enset.backend.enums.CreditStatus;

import java.util.List;

public interface CreditService {
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getAllCredits();
    CreditDTO saveCredit(CreditDTO creditDTO);
    CreditDTO updateCredit(CreditDTO creditDTO);
    void deleteCredit(Long id);

    PersonalCreditDTO savePersonalCredit(PersonalCreditDTO personalCreditDTO);
    ProfessionalCreditDTO saveProfessionalCredit(ProfessionalCreditDTO professionalCreditDTO);
    RealEstateCreditDTO saveRealEstateCredit(RealEstateCreditDTO realEstateCreditDTO);

    List<CreditDTO> getCreditsByClientId(Long clientId);
    List<CreditDTO> getCreditsByStatus(CreditStatus status);
    CreditDTO updateCreditStatus(Long creditId, CreditStatus status);

    double calculateTotalCreditAmount();
}
