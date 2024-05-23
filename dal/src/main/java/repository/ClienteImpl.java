package repository;

import isel.sisinf.jpa.Cliente;

import java.util.Collection;

public interface ClienteImpl extends IRepository<Cliente, Collection<Cliente>, Long>, IClientDataMapper {

}
