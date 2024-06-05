package repository;

import isel.sisinf.jpa.Reserva;

import java.sql.Timestamp;
import java.util.List;

public interface ReservaImpl {
    Reserva findByKey(Long key);
    List<Reserva> findAll();
    Reserva create(Reserva entity);
    Reserva update(Reserva entity);
    void delete(Reserva entity);

    void createReservaWithStoredProcedure(int lojaId, int clienteId, int bicicletaId, Timestamp dataInicio, Timestamp dataFim, double valor);

    void cancelarReservaWithStoredProcedure(int reservaId);
}
