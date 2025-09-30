package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.megalab.smart_kindergarten_management.models.enums.TeacherDegree;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher extends BaseEntity {

    @NotBlank(message = "Имя обязательно")
    String firstName;

    @NotBlank(message = "Фамилия обязательна")
    String lastName;

    String patronymic;

    String phoneNumber;

    @NotNull(message = "Степень/роль обязательна")
    @Enumerated(EnumType.STRING)
    TeacherDegree teacherDegree;

    @NotNull(message = "Статус обязателен")
    Boolean active;

    @OneToMany(mappedBy = "teacher")
    List<Group> groups;

}
