package repository;

import isel.sisinf.jpa.Bicicleta;
import isel.sisinf.jpa.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BicycleRepository implements BicycleImpl {

    private final EntityManager entityManager;

    public BicycleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Bicicleta getBicycleById(int id) {
        return entityManager.find(Bicicleta.class, id);
    }

    @Override
    public List<Bicicleta> getBicycles() {
        String query = "SELECT b FROM Bicicletas b";

        TypedQuery<Bicicleta> queryEm = entityManager.createQuery(query, Bicicleta.class);
        List<Bicicleta> listOfBicycles = queryEm.getResultList();
        return listOfBicycles;
//        return entityManager.createQuery(query, Bicicleta.class)
//
//                .getResultList();
    }


    @Override
    public void saveBicycle(Bicicleta bicycle) {
        entityManager.getTransaction().begin();
        entityManager.persist(bicycle);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteBicycle(int id) {
        Bicicleta bicycle = getBicycleById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(bicycle);
        entityManager.getTransaction().commit();
    }
}
