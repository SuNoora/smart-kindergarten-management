package kg.megalab.smart_kindergarten_management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "children")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Child extends BaseEntity{

    @NotBlank(message = "Имя обязательно")
    @Column(name = "first_name", nullable = false)
    String firstName;

    @NotBlank(message = "Фамилия обязательна")
    @Column(name = "last_name", nullable = false)
    String lastName;

    String patronymic;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    String parentFullName;
    String parentPhone;

    @OneToMany(mappedBy = "child")
    @JsonIgnoreProperties("child")
    List<Enrollment> enrollments;
}