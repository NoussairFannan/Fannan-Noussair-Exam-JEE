package ma.enset.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private CreditStatus status;

    private Date acceptanceDate;

    private Double amount;
    private Integer repaymentDuration;
    private Double interestRate;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Repayment> repayments = new ArrayList<>();


}
