package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "groups")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends BaseEntity{

    @NotBlank(message = "Название группы обязательно")
    String name;

    @Column(name = "max_children_count")
    @NotNull(message = "Макс. количество детей обязательно")
    @Positive(message = "Количество должно быть положительным")
    Integer maxChildrenCount;

    @Column(name = "min_children_count")
    Integer minChildrenCount;

    @Column(name = "price")
    @NotNull(message = "Цена обязательна")
    @Positive(message = "Цена должна быть положительной")
    Double price;

    @Column(name = "active")
    Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "group_category_id", nullable = false)
    GroupCategory groupCategory;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "nanny_id")
    Teacher nanny;

    @OneToMany(mappedBy = "group")
    List<Enrollment> enrollments;
}