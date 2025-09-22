package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    Child child;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    Group group;

    @OneToMany
    List<Payment> payments;
}
