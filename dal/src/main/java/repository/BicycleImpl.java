package repository;

import isel.sisinf.jpa.Bicicleta;
import isel.sisinf.jpa.Cliente;

import java.util.List;

public interface BicycleImpl {

    Bicicleta getBicycleById(int id);
    List<Bicicleta> getBicycles();
    void saveBicycle(Bicicleta bicycle);
    void deleteBicycle(int id);
}
