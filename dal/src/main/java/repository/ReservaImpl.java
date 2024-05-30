package repository;

import isel.sisinf.jpa.Reserva;
import java.util.List;

public interface ReservaImpl {
    Reserva findByKey(Long key);
    List<Reserva> findAll();
    Reserva create(Reserva entity);
    Reserva update(Reserva entity);
    void delete(Reserva entity);
}
