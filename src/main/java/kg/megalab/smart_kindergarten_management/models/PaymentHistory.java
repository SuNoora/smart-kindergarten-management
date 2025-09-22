package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentHistory extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;
    @NotNull
    @PositiveOrZero
    Double amount;
}
