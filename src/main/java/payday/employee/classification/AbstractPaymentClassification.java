package payday.employee.classification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import payday.Paycheck;
import payday.employee.PaymentClassification;
import payday.util.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance
@DiscriminatorColumn
@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractPaymentClassification implements PaymentClassification {
    @Id
    @GeneratedValue
    private Integer classificationId;

}
