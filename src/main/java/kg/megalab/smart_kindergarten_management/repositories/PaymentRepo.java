package kg.megalab.smart_kindergarten_management.repositories;

import kg.megalab.smart_kindergarten_management.models.Enrollment;
import kg.megalab.smart_kindergarten_management.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    List<Payment> findByEnrollment(Enrollment enrollment);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.enrollment = :enrollment AND p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    Double sumPaymentsByEnrollmentAndDateRange(@Param("enrollment") Enrollment enrollment,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
}