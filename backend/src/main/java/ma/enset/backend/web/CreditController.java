package ma.enset.backend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.enset.backend.dtos.CreditDTO;
import ma.enset.backend.dtos.PersonalCreditDTO;
import ma.enset.backend.dtos.ProfessionalCreditDTO;
import ma.enset.backend.dtos.RealEstateCreditDTO;
import ma.enset.backend.enums.CreditStatus;
import ma.enset.backend.services.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
@CrossOrigin("*")
public class CreditController {
    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    public ResponseEntity<List<CreditDTO>> getAllCredits() {
        return ResponseEntity.ok(creditService.getAllCredits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.getCreditById(id));
    }

    @PostMapping
    public ResponseEntity<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO) {
        return new ResponseEntity<>(creditService.saveCredit(creditDTO), HttpStatus.CREATED);
    }

    @PostMapping("/personal")
    public ResponseEntity<PersonalCreditDTO> createPersonalCredit(@RequestBody PersonalCreditDTO personalCreditDTO) {
        return new ResponseEntity<>(creditService.savePersonalCredit(personalCreditDTO), HttpStatus.CREATED);
    }

    @PostMapping("/professional")
    public ResponseEntity<ProfessionalCreditDTO> createProfessionalCredit(@RequestBody ProfessionalCreditDTO professionalCreditDTO) {
        return new ResponseEntity<>(creditService.saveProfessionalCredit(professionalCreditDTO), HttpStatus.CREATED);
    }

    @PostMapping("/real-estate")
    public ResponseEntity<RealEstateCreditDTO> createRealEstateCredit(@RequestBody RealEstateCreditDTO realEstateCreditDTO) {
        return new ResponseEntity<>(creditService.saveRealEstateCredit(realEstateCreditDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditDTO> updateCredit(@PathVariable Long id, @RequestBody CreditDTO creditDTO) {
        creditDTO.setId(id);
        return ResponseEntity.ok(creditService.updateCredit(creditDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CreditDTO>> getCreditsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(creditService.getCreditsByClientId(clientId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CreditDTO>> getCreditsByStatus(@PathVariable CreditStatus status) {
        return ResponseEntity.ok(creditService.getCreditsByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CreditDTO> updateCreditStatus(@PathVariable Long id, @RequestParam CreditStatus status) {
        return ResponseEntity.ok(creditService.updateCreditStatus(id, status));
    }

    @GetMapping("/total-amount")
    public ResponseEntity<Double> calculateTotalCreditAmount() {
        return ResponseEntity.ok(creditService.calculateTotalCreditAmount());
    }

}