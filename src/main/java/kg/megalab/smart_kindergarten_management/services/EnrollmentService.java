package kg.megalab.smart_kindergarten_management.services;

import kg.megalab.smart_kindergarten_management.models.dto.EnrollChildDto;
import kg.megalab.smart_kindergarten_management.models.dto.WithdrawChildDto;
import kg.megalab.smart_kindergarten_management.models.Enrollment;

public interface EnrollmentService {

    Enrollment enrollChild(EnrollChildDto enrollChildDto);

    Enrollment withdrawChild(Long enrollmentId, WithdrawChildDto withdrawChildDto);
}