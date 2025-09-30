package kg.megalab.smart_kindergarten_management.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.smart_kindergarten_management.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepo extends JpaRepository<Child, Long> {

    Optional<Child> findByFirstNameAndLastName(@NotBlank(message = "Имя обязательно") String firstName, @NotBlank(message = "Фамилия обязательна") String lastName);
}
