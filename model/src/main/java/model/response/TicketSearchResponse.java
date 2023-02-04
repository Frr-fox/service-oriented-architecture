package model.response;

import lombok.*;
import model.dto.TicketDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketSearchResponse {
    List<TicketDTO> tickets;
}
