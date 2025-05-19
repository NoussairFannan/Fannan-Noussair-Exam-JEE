package ma.enset.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCredit extends Credit {
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    
    public enum PropertyType {
        APARTMENT, HOUSE, COMMERCIAL_PROPERTY
    }
}