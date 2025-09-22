package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    Enrollment enrollment;
    @NotNull
    @PositiveOrZero
    Double amount;
    @NotNull
    String description;
    LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany
    List<PaymentHistory> paymentHistories;

}
