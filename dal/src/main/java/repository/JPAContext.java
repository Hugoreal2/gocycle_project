package repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import isel.sisinf.jpa.Bicicleta;
import isel.sisinf.jpa.Cliente;
import isel.sisinf.jpa.Reserva;
import jakarta.persistence.*;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;

public class JPAContext implements IContext {
    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private BicycleImpl _bicycleRepository;
    private ClienteImpl _clientRepository;
    private ReservaImpl _reservaRepository;

    /// HELPER METHODS
    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for (int i = 0; i < params.length; ++i)
            q.setParameter(i + 1, params[i]);

        return q.getResultList();
    }

    protected Object helperCreateImpl(Object entity) {
        beginTransaction();
        _em.persist(entity);
        commit();
        return entity;
    }

    protected Object helperUpdateImpl(Object entity) {
        beginTransaction();
        _em.merge(entity);
        commit();
        return entity;
    }

    protected Object helperDeleteteImpl(Object entity) {
        beginTransaction();
        _em.remove(entity);
        commit();
        return entity;
    }
    /// END HELPER

    @Override
    public void beginTransaction() {
        if (_tx == null) {
            _tx = _em.getTransaction();
            _tx.begin();
            _txcount = 0;
        }
        ++_txcount;
    }

    @Override
    public void beginTransaction(IsolationLevel isolationLevel) {
        beginTransaction();
        Session session = _em.unwrap(Session.class);
        DatabaseLogin databaseLogin = (DatabaseLogin) session.getDatasourceLogin();
        System.out.println(databaseLogin.getTransactionIsolation());

        int isolation = DatabaseLogin.TRANSACTION_READ_COMMITTED;
        if (isolationLevel == IsolationLevel.READ_UNCOMMITTED)
            isolation = DatabaseLogin.TRANSACTION_READ_UNCOMMITTED;
        else if (isolationLevel == IsolationLevel.REPEATABLE_READ)
            isolation = DatabaseLogin.TRANSACTION_REPEATABLE_READ;
        else if (isolationLevel == IsolationLevel.SERIALIZABLE)
            isolation = DatabaseLogin.TRANSACTION_SERIALIZABLE;

        databaseLogin.setTransactionIsolation(isolation);
    }

    @Override
    public void commit() {
        --_txcount;
        if (_txcount == 0 && _tx != null) {
            _em.flush();
            _tx.commit();
            _tx = null;
        }
    }

    @Override
    public void flush() {
        _em.flush();
    }

    @Override
    public void clear() {
        _em.clear();
    }

    @Override
    public void persist(Object entity) {
        _em.persist(entity);
    }

    @Override
    public BicycleImpl getBiciclesRepo() {
        return _bicycleRepository;
    }

    @Override
    public ClienteImpl getClientesRepo() {
        return _clientRepository;
    }

    @Override
    public ReservaImpl getReservasRepo() {
        return _reservaRepository;
    }

    public JPAContext() {
        this("common");
    }

    public JPAContext(String persistentCtx) {
        super();
        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._bicycleRepository = new BicycleRepository();
        this._clientRepository = new ClientRepository();
        this._reservaRepository = new ReservaRepository();
    }

    @Override
    public void close() throws Exception {
        if (_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }

    public class ClientRepository implements ClienteImpl {
        public ClientRepository() {
        }

        @Override
        public Cliente findByKey(Long key) {
            return _em.find(Cliente.class, key);
        }

        @Override
        public Collection<Cliente> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Cliente create(Cliente entity) {
            return (Cliente) helperCreateImpl(entity);
        }

        @Override
        public Cliente update(Cliente entity) {
            return (Cliente) helperUpdateImpl(entity);
        }

        @Override
        public Cliente delete(Cliente entity) {
            return (Cliente) helperDeleteteImpl(entity);
        }
    }

    public class BicycleRepository implements BicycleImpl {

        @Override
        public Bicicleta findByKey(Long key) {
            return _em.find(Bicicleta.class, key);
        }

        @Override
        public Collection<Bicicleta> find(String jpql, Object... params) {
            return helperQueryImpl(jpql, params);
        }

        @Override
        public Bicicleta create(Bicicleta entity) {
            return (Bicicleta) helperCreateImpl(entity);
        }

        @Override
        public Bicicleta update(Bicicleta entity) {
            return (Bicicleta) helperUpdateImpl(entity);
        }

        @Override
        public Bicicleta delete(Bicicleta entity) {
            return (Bicicleta) helperDeleteteImpl(entity);
        }

        @Override
        public List<Bicicleta> getBicycles() {
            return _em.createQuery("SELECT b FROM Bicicleta b", Bicicleta.class).getResultList();
        }

        @Override
        public Boolean podeSerReservado(Integer bicicleta_id, Timestamp data_inicio, Timestamp data_fim) {
            try {
                StoredProcedureQuery query = _em.createStoredProcedureQuery("pode_ser_reservado")
                        .registerStoredProcedureParameter(1, Integer.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(2, Timestamp.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(3, Timestamp.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(4, Boolean.class, jakarta.persistence.ParameterMode.OUT)
                        .setParameter(1, bicicleta_id)
                        .setParameter(2, data_inicio)
                        .setParameter(3, data_fim);

                query.execute();
                Boolean result = (Boolean) query.getOutputParameterValue(4);
                return result;
            } finally {
                // Do nothing
            }
        }
    }
    public class ReservaRepository implements ReservaImpl {

        @Override
        public Reserva findByKey(Long key) {
            return _em.find(Reserva.class, key);
        }

        @Override
        public List<Reserva> findAll() {
            return _em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
        }

        @Override
        public Reserva create(Reserva entity) {
            _em.getTransaction().begin();
            _em.persist(entity);
            _em.getTransaction().commit();
            return entity;
        }

        public void createReservaWithStoredProcedure(int lojaId, int clienteId, int bicicletaId, Timestamp dataInicio, Timestamp dataFim, double valor) {
            EntityTransaction transaction = _em.getTransaction();
            try {
                transaction.begin();
                StoredProcedureQuery query = _em.createStoredProcedureQuery("adicionar_reserva")
                        .registerStoredProcedureParameter(1, Integer.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(2, Integer.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(3, Integer.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(4, Timestamp.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(5, Timestamp.class, jakarta.persistence.ParameterMode.IN)
                        .registerStoredProcedureParameter(6, Double.class, jakarta.persistence.ParameterMode.IN)
                        .setParameter(1, lojaId)
                        .setParameter(2, clienteId)
                        .setParameter(3, bicicletaId)
                        .setParameter(4, dataInicio)
                        .setParameter(5, dataFim)
                        .setParameter(6, valor);

                query.execute();
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error creating reservation with stored procedure", e);
            }
        }


        @Override
        public Reserva update(Reserva entity) {
            _em.getTransaction().begin();
            _em.merge(entity);
            _em.getTransaction().commit();
            return entity;
        }

        @Override
        public void delete(Reserva entity) {
            _em.getTransaction().begin();
            _em.remove(entity);
            _em.getTransaction().commit();
        }
    }

}
