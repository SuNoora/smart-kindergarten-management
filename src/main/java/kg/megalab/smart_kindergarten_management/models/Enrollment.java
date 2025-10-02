package kg.megalab.smart_kindergarten_management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "enrollments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment extends BaseEntity{

    @Column(name = "start_date")
    LocalDate startDate = LocalDate.now();

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "price")
    @NotNull(message = "Цена обязательна")
    @PositiveOrZero(message = "Цена должна быть положительной")
    Double price;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    @JsonIgnoreProperties("enrollments")
    Child child;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnoreProperties("enrollments")
    Group group;

    @OneToMany(mappedBy = "enrollment")
    @JsonIgnoreProperties("enrollment")
    List<Payment> payments;
}