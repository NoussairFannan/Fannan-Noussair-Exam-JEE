package ma.enset.backend.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCredit extends Credit {
    private String purpose; // e.g., car purchase, studies, renovations
}