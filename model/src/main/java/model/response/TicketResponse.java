package model.response;

import lombok.*;
import model.dto.ErrorDTO;
import model.dto.TicketDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TicketResponse {
    TicketDTO ticket;
    ErrorDTO error;
}
