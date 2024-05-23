package repository;


import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import isel.sisinf.jpa.Bicicleta;
import isel.sisinf.jpa.Cliente;
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
        beginTransaction(); //Each write can have multiple inserts on the DB. See the relations.
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
            _em.flush(); //To assure all changes in memory go into the database
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

    public JPAContext() {
        this("common");
    }

    public JPAContext(String persistentCtx) {
        super();

        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        //this._countryRepository = new CountryRepository();
        this._bicycleRepository = new BicycleRepository();
        this._clientRepository = new ClientRepository();
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
            return _em.createNamedQuery("Client.findByKey",Cliente.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @Override
        public Collection<Cliente> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public Cliente create(Cliente entity) {
            return (Cliente)helperCreateImpl(entity);
        }

        @Override
        public Cliente update(Cliente entity) {
            return (Cliente)helperUpdateImpl(entity);
        }

        @Override
        public Cliente delete(Cliente entity) {
            return (Cliente)helperDeleteteImpl(entity);
        }
    }

    public class BicycleRepository implements BicycleImpl {

        @Override
        public Bicicleta findByKey(Long key) {
            return _em.createNamedQuery("Bicicleta.findByKey",Bicicleta.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @Override
        public Collection<Bicicleta> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public Bicicleta create(Bicicleta entity) {
            return (Bicicleta)helperCreateImpl(entity);
        }

        @Override
        public Bicicleta update(Bicicleta entity) {
            return (Bicicleta)helperUpdateImpl(entity);
        }

        @Override
        public Bicicleta delete(Bicicleta entity) {
            return (Bicicleta)helperDeleteteImpl(entity);
        }

        @Override
        public List<Bicicleta> getBicycles() {
            return _em.createNamedQuery("Bicicleta.findAll",Bicicleta.class)
                    .getResultList();
        }

        @Override
        public Boolean podeSerReservado(Integer bicicleta_id, Timestamp data_inicio, Timestamp data_fim) {
            StoredProcedureQuery query = _em.createNamedStoredProcedureQuery("Bicicleta.podeSerReservado");
            query.setParameter("p_bicicleta_id", bicicleta_id);
            query.setParameter("p_data_inicio", data_inicio);
            query.setParameter("p_data_fim", data_fim);

            query.execute();

            return (Boolean) query.getOutputParameterValue("result");
        }
    }

}
