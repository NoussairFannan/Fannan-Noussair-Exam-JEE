package ma.enset.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.backend.enums.PropertyType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCreditDTO extends CreditDTO {
    private PropertyType propertyType;
}