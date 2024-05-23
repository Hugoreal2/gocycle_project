package repository;

import isel.sisinf.jpa.Bicicleta;

import java.util.Collection;
import java.util.List;

public interface BicycleImpl extends IRepository<Bicicleta, Collection<Bicicleta>, Long>, IBicycleDataMapper {
    List<Bicicleta> getBicycles();
}
