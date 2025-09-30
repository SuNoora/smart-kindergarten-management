package kg.megalab.smart_kindergarten_management.services.impl;

import kg.megalab.smart_kindergarten_management.exceptions.NotFoundException;
import kg.megalab.smart_kindergarten_management.mappers.PaymentMapper;
import kg.megalab.smart_kindergarten_management.models.*;
import kg.megalab.smart_kindergarten_management.models.dto.PaymentDto;
import kg.megalab.smart_kindergarten_management.models.dto.PreviousMonthDto;
import kg.megalab.smart_kindergarten_management.repositories.*;
import kg.megalab.smart_kindergarten_management.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final ChildRepo childRepo;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public Payment createPayment(PaymentDto paymentDto) {
        // Проверяем существование записи о зачислении
        Enrollment enrollment = enrollmentRepo.findById(paymentDto.getGroupChildrenId())
                .orElseThrow(() -> new NotFoundException("Запись о ребенке в группе не найдена!"));

        // Создаем платеж
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        payment.setEnrollment(enrollment);
        payment.setDescription("Оплата за садик");

        return paymentRepo.save(payment);
    }

    @Override
    public PreviousMonthDto getPreviousMonth(Long childId) {
        // Проверяем существование ребенка
        Child child = childRepo.findById(childId)
                .orElseThrow(() -> new NotFoundException("Ребенок не найден!"));

        // Ищем активную запись о зачислении
        Enrollment enrollment = enrollmentRepo.findActiveEnrollmentByChildId(childId)
                .orElseThrow(() -> new NotFoundException("Активная группа для ребенка не найдена!"));

        // Определяем прошлый месяц
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        LocalDate monthStart = previousMonth.atDay(1);
        LocalDate monthEnd = previousMonth.atEndOfMonth();

        // Проверяем, была ли запись активна в прошлом месяце
        if (enrollment.getStartDate().isAfter(monthEnd) ||
                (enrollment.getEndDate() != null && enrollment.getEndDate().isBefore(monthStart))) {
            throw new NotFoundException("Ребенок не был зачислен в группу в прошлом месяце!");
        }

        // Рассчитываем задолженность
        Double monthlyPrice = enrollment.getPrice();
        Double paidAmount = paymentRepo.sumPaymentsByEnrollmentAndDateRange(enrollment, monthStart, monthEnd);

        // Рассчитываем количество дней в группе за месяц (если зачислен не с начала месяца)
        LocalDate effectiveStartDate = enrollment.getStartDate().isAfter(monthStart)
                ? enrollment.getStartDate()
                : monthStart;
        LocalDate effectiveEndDate = enrollment.getEndDate() != null && enrollment.getEndDate().isBefore(monthEnd)
                ? enrollment.getEndDate()
                : monthEnd;

        long daysInMonth = java.time.temporal.ChronoUnit.DAYS.between(effectiveStartDate, effectiveEndDate) + 1;
        long totalDaysInMonth = monthStart.lengthOfMonth();

        // Пропорциональная стоимость за фактически проведенные дни
        Double proportionalPrice = monthlyPrice * daysInMonth / totalDaysInMonth;

        Integer amountDue = (int) Math.max(0, proportionalPrice - (paidAmount != null ? paidAmount : 0));

        return new PreviousMonthDto(childId, amountDue);
    }
}