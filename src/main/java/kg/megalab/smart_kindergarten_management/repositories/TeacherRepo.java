package kg.megalab.smart_kindergarten_management.repositories;

import kg.megalab.smart_kindergarten_management.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    boolean existsByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

}