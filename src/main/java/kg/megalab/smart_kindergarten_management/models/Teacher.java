package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher extends BaseEntity {

    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    String phoneNumber;
    String degree;
    Boolean active;

    @OneToMany
    List<Group> groups;

}
