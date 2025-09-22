package kg.megalab.smart_kindergarten_management.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends BaseEntity{

    @NotBlank
    String name;
    @Column(name = "max_children_count")
    Integer maxChildrenCount;
    @Column(name = "min_children_count")
    Integer childrenCount;
    Boolean active;

    @ManyToOne
    @JoinColumn(name = "group_category_id", nullable = false)
    GroupCategory groupCategory;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "group")
    List<Enrollment> enrollments;

}

