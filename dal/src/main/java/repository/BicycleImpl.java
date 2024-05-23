package repository;

import isel.sisinf.jpa.Bicicleta;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface BicycleImpl extends IRepository<Bicicleta, Collection<Bicicleta>, Long>, IBicycleDataMapper {
    List<Bicicleta> getBicycles();
    Boolean podeSerReservado(Integer bicicleta_id, Timestamp data_inicio, Timestamp data_fim);
}
