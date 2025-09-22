package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "children")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Child extends BaseEntity{

    @NotBlank
    String firstName;
    @NotBlank
    String lastName;

    LocalDate dateOfBirth;

    String parentFullName;
    String parentPhone;

    @OneToMany(mappedBy = "child")
    List<Enrollment> enrollments;
}
