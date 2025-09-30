package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    Enrollment enrollment;

    @NotNull(message = "Сумма обязательна")
    @PositiveOrZero(message = "Сумма должна быть положительной")
    Double amount;

    @Column(name = "payment_date")
    @NotNull(message = "Дата платежа обязательна")
    LocalDate paymentDate;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "payment")
    List<PaymentHistory> paymentHistories;
}