package ma.enset.backend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.enset.backend.dtos.RepaymentDTO;
import ma.enset.backend.services.RepaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/repayments")
@CrossOrigin("*")
public class RepaymentController {
    private final RepaymentService repaymentService;

    public RepaymentController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    @GetMapping
    public ResponseEntity<List<RepaymentDTO>> getAllRepayments() {
        return ResponseEntity.ok(repaymentService.getAllRepayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepaymentDTO> getRepaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(repaymentService.getRepaymentById(id));
    }

    @PostMapping
    public ResponseEntity<RepaymentDTO> createRepayment(@RequestBody RepaymentDTO repaymentDTO) {
        return new ResponseEntity<>(repaymentService.saveRepayment(repaymentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepaymentDTO> updateRepayment(@PathVariable Long id, @RequestBody RepaymentDTO repaymentDTO) {
        repaymentDTO.setId(id);
        return ResponseEntity.ok(repaymentService.updateRepayment(repaymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepayment(@PathVariable Long id) {
        repaymentService.deleteRepayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/credit/{creditId}")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentsByCreditId(@PathVariable Long creditId) {
        return ResponseEntity.ok(repaymentService.getRepaymentsByCreditId(creditId));
    }


    @GetMapping("/credit/{creditId}/total-amount")
    public ResponseEntity<Double> calculateTotalRepaymentAmount(@PathVariable Long creditId) {
        return ResponseEntity.ok(repaymentService.calculateTotalRepaymentAmount(creditId));
    }

}