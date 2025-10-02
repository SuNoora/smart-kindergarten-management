package kg.megalab.smart_kindergarten_management.repositories;

import kg.megalab.smart_kindergarten_management.models.Child;
import kg.megalab.smart_kindergarten_management.models.Enrollment;
import kg.megalab.smart_kindergarten_management.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

    long countByGroupAndEndDateIsNull(Group group);

    Optional<Enrollment> findByChildAndEndDateIsNull(Child child);

    @Query("SELECT e FROM Enrollment e WHERE e.child.id = :childId AND e.endDate IS NULL")
    Optional<Enrollment> findActiveEnrollmentByChildId(@Param("childId") Long childId);
}