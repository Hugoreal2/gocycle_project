package repository;

import isel.sisinf.jpa.Cliente;

import java.util.List;

public interface ClienteImpl {

    Cliente getClienteById(int id);
    List<Cliente> getClientes();
    void saveCliente(Cliente cliente);
    void deleteCliente(int id);
}
