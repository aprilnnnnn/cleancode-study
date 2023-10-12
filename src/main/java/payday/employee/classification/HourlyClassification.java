package payday.employee.classification;

import lombok.*;
import payday.Paycheck;
import payday.util.DateUtils;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@DiscriminatorValue("H")
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HourlyClassification extends AbstractPaymentClassification {
    private static final double OVERTIME_LIMIT_HOURS = 8.0;
    private static final double OVERTIME_RATE = 1.5;
    @Getter
    private double rate;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TimeCard> timeCards = new ArrayList<>();

    public HourlyClassification(double rate) {
        super();
        this.rate = rate;
    }

    public TimeCard getTimeCard(long timeMillis) {
        if (timeCards == null) {
            return null;
        }
        return getTimeCards().stream()
            .filter(tc -> tc.equalsTimeMillis(timeMillis))
            .findAny()
            .orElse(null);
    }

    public void addTimeCard(@NonNull TimeCard timeCard) {
        getTimeCards().add(timeCard);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalPay = 0.0;
        for (TimeCard timeCard : getTimeCards()) {
            if (!isInPayPeriod(timeCard.getDate(), pc)) {
                continue;
            }
            totalPay += calculatePayForTimeCard(timeCard);
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        final double hours = timeCard.getHours();
        final double overtime = Math.max(0.0, hours - OVERTIME_LIMIT_HOURS);
        final double straightTime = hours - overtime;
        return (straightTime * rate) + (overtime * rate * OVERTIME_RATE);
    }

    boolean isInPayPeriod(@NonNull Date payDate, @NonNull Paycheck pc) {
        return DateUtils.between(payDate, pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
    }
}
