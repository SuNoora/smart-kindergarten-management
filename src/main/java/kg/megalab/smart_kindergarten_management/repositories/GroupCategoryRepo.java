package kg.megalab.smart_kindergarten_management.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.smart_kindergarten_management.models.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoryRepo extends JpaRepository<GroupCategory, Long> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Название категории обязательно!") String name);

    Long id(long id);

}
