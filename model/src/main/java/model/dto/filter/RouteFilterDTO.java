package model.dto.filter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteFilterDTO {
    Long id;
    String name;
    Long x;
    Double y;
    String creationDate;
    Float fromX;
    Long fromY;
    Integer fromZ;
    Long toX;
    Integer toY;
    Long toZ;
    String toName;
    Long distance;
    String sortBy;
    String order;
    Integer page;
    Integer limit;
}
