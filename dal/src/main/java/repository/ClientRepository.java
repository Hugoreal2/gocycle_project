package repository;

import isel.sisinf.jpa.Cliente;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClientRepository implements ClienteImpl {

    private final EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cliente getClienteById(int id) {
        return entityManager.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> getClientes() {
        String query = "FROM Cliente";
        return entityManager.createQuery(query, Cliente.class).getResultList();
    }

    @Override
    public void saveCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteCliente(int id) {
        Cliente cliente = getClienteById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }
}
