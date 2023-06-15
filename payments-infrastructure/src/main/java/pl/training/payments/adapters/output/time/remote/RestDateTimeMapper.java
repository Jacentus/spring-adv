package pl.training.payments.adapters.output.time.remote;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RestDateTimeMapper {

    ZoneId ZONE_ID = ZoneId.of("Europe/Warsaw");

    default ZonedDateTime toDomain(DateTimeDto dateTimeDto) {
        var instant = Instant.ofEpochSecond(dateTimeDto.getUnixtime());
        return ZonedDateTime.ofInstant(instant, ZONE_ID);
    }

}
