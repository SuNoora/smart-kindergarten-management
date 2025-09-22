package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "group_categories")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupCategory extends BaseEntity{

    @NotBlank
    String name;
    Boolean active;
    @NotNull
    @PositiveOrZero
    Double price;

    @OneToMany
    List<Group> groups;
}
