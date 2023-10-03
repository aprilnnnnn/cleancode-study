package payday.employee.affilliation.command;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ServiceCharge {
    @Id
    private Long dates;
    private double amount;

    public boolean equalsTimeMillis(Long dates) {
        return this.dates != null && this.dates.equals(dates);
    }
}
