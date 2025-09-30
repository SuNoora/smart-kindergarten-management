package kg.megalab.smart_kindergarten_management.services.impl;

import kg.megalab.smart_kindergarten_management.exceptions.ConflictException;
import kg.megalab.smart_kindergarten_management.exceptions.NotFoundException;
import kg.megalab.smart_kindergarten_management.mappers.EnrollmentMapper;
import kg.megalab.smart_kindergarten_management.models.*;
import kg.megalab.smart_kindergarten_management.models.dto.EnrollChildDto;
import kg.megalab.smart_kindergarten_management.models.dto.WithdrawChildDto;
import kg.megalab.smart_kindergarten_management.repositories.*;
import kg.megalab.smart_kindergarten_management.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final ChildRepo childRepo;
    private final GroupRepo groupRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    @Transactional
    public Enrollment enrollChild(EnrollChildDto enrollChildDto) {

        Group group = groupRepo.findById(enrollChildDto.getGroupId())
                .orElseThrow(() -> new NotFoundException("Группа не найдена!"));

        if (!Boolean.TRUE.equals(group.getActive())) {
            throw new ConflictException("Группа неактивна!");
        }

        long currentChildrenCount = enrollmentRepo.countByGroupAndEndDateIsNull(group);
        if (currentChildrenCount >= group.getMaxChildrenCount()) {
            throw new ConflictException("Группа заполнена! Достигнут максимальный лимит детей.");
        }

        //  Поиск существующего ребенка или создаем нового
        Child child = findOrCreateChild(enrollChildDto);

        Enrollment enrollment = enrollmentMapper.enrollChildDtoToEnrollment(enrollChildDto);
        enrollment.setChild(child);
        enrollment.setGroup(group);

        // Устанавливаем цену (индивидуальная или из группы)
        if (enrollChildDto.getPrice() != null) {
            enrollment.setPrice(enrollChildDto.getPrice().doubleValue());
        } else {
            enrollment.setPrice(group.getPrice());
        }

        return enrollmentRepo.save(enrollment);
    }

    @Override
    @Transactional
    public Enrollment withdrawChild(Long enrollmentId, WithdrawChildDto withdrawChildDto) {
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Запись о зачислении ребенка не найдена!"));

        if (enrollment.getEndDate() != null) {
            throw new ConflictException("Ребенок уже отчислен из группы!");
        }

        LocalDate endDate = withdrawChildDto.getEndDate();
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        enrollment.setEndDate(endDate);
        return enrollmentRepo.save(enrollment);
    }

    private Child findOrCreateChild(EnrollChildDto enrollChildDto) {

        return childRepo.findByFirstNameAndLastName(
                        enrollChildDto.getFirstName(),
                        enrollChildDto.getLastName())
                .orElseGet(() -> {

                    Child newChild = new Child();
                    newChild.setFirstName(enrollChildDto.getFirstName());
                    newChild.setLastName(enrollChildDto.getLastName());
                    newChild.setDateOfBirth(enrollChildDto.getDateOfBirth());
                    return childRepo.save(newChild);
                });
    }
}