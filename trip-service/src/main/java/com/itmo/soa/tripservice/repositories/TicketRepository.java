package com.itmo.soa.tripservice.repositories;

import lombok.NonNull;
import model.entity.Route;
import model.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAllByPassengerId(@Size(max = 8) String passenger_id);

    List<Ticket> findAllByDirectionAndDepartureDateAndPlace(@NonNull Route direction,
                                                            @NonNull LocalDateTime departureDate,
                                                            String place);

    List<Ticket> findAllByDirection_Id(long direction_id);

    void deleteAllByDirection_Id(long direction_id);
}
