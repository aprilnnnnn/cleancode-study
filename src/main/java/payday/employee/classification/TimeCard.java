package payday.employee.classification;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeCard {
    @Id
    private Long dates;
    private double hours;

    public boolean equalsTimeMillis(Long dates) {
        return this.dates != null && this.dates.equals(dates);
    }

    public Date getDate() {
        return new Date(dates);
    }
}
