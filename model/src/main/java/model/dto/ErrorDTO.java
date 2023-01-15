package model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDTO {
    private Integer code;
    private String message;
    private LocalDateTime time;
}
