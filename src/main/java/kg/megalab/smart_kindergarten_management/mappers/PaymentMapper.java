package kg.megalab.smart_kindergarten_management.mappers;

import kg.megalab.smart_kindergarten_management.models.Payment;
import kg.megalab.smart_kindergarten_management.models.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    @Mapping(target = "paymentHistories", ignore = true)
    @Mapping(target = "description", ignore = true)
    Payment paymentDtoToPayment(PaymentDto paymentDto);
}