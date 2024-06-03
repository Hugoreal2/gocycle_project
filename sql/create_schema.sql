-- DROP ALL TABLES
DROP TABLE IF EXISTS reserva CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS bicicleta CASCADE;
DROP TABLE IF EXISTS gps CASCADE;
DROP TABLE IF EXISTS loja CASCADE;


-- Criação das tabelas

-- Tabela de lojas
CREATE TABLE IF NOT EXISTS loja (
                       codigo SERIAL PRIMARY KEY,
                       gestor VARCHAR(100) NOT NULL,
                       morada VARCHAR(255) NOT NULL,
                       telefone VARCHAR(15) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de dispositivos GPS
CREATE TABLE IF NOT EXISTS gps (
                     id SERIAL PRIMARY KEY,
                     numero_serie VARCHAR(100) NOT NULL UNIQUE,
                     latitude DECIMAL(9,6) NOT NULL,
                     longitude DECIMAL(9,6) NOT NULL,
                     percentagem_bateria DECIMAL(5,2) NOT NULL
    --bicicleta_id INTEGER UNIQUE,
    --FOREIGN KEY (bicicleta_id) REFERENCES bicicletas(id) ON DELETE SET NULL
);


-- Tabela de bicicletas
CREATE TABLE IF NOT EXISTS bicicleta (
                            id SERIAL PRIMARY KEY,
                            tipo VARCHAR(10) CHECK (tipo IN ('classica', 'eletrica')) NOT NULL,
                            peso INTEGER NOT NULL,
                            modelo VARCHAR(100) NOT NULL,
                            marca VARCHAR(100) NOT NULL,
                            sistema_mudancas VARCHAR(10) NOT NULL,
                            estado VARCHAR(20) CHECK (estado IN ('livre', 'ocupado', 'em reserva', 'em manutencao')) NOT NULL,
                            autonomia INTEGER,
                            velocidade_maxima INTEGER,
                            id_gps INTEGER UNIQUE,
                            ativo BOOLEAN DEFAULT TRUE,
                            FOREIGN KEY (id_gps) REFERENCES gps(id) ON DELETE SET NULL
);


-- Tabela de clientes
CREATE TABLE IF NOT EXISTS cliente (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          morada VARCHAR(255) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          telefone VARCHAR(15) NOT NULL,
                          cc_passaporte VARCHAR(20) NOT NULL,
                          nacionalidade VARCHAR(50) NOT NULL,
                          ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de reservas
CREATE TABLE IF NOT EXISTS reserva (
                          numero SERIAL PRIMARY KEY,
                          loja_id INTEGER NOT NULL,
                          cliente_id INTEGER NOT NULL,
                          bicicleta_id INTEGER NOT NULL,
                          data_inicio TIMESTAMP NOT NULL,
                          data_fim TIMESTAMP NOT NULL,
                          valor DECIMAL(10,2) NOT NULL,
                          FOREIGN KEY (loja_id) REFERENCES loja(codigo),
                          FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                          FOREIGN KEY (bicicleta_id) REFERENCES bicicleta(id),
                          UNIQUE (loja_id, numero),
                          CONSTRAINT reserva_unica_cliente_data UNIQUE (cliente_id, data_inicio)
);

-- Triggers para remoção lógica
CREATE OR REPLACE FUNCTION set_inactive() RETURNS TRIGGER AS $$
BEGIN
    NEW.ativo := FALSE;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Aplicar remoção lógica em lojas, clientes e bicicletas
CREATE OR REPLACE TRIGGER soft_delete_lojas
    BEFORE DELETE ON loja
    FOR EACH ROW
EXECUTE FUNCTION set_inactive();

CREATE OR REPLACE TRIGGER soft_delete_clientes
    BEFORE DELETE ON cliente
    FOR EACH ROW
EXECUTE FUNCTION set_inactive();

CREATE OR REPLACE TRIGGER soft_delete_bicicletas
    BEFORE DELETE ON bicicleta
    FOR EACH ROW
EXECUTE FUNCTION set_inactive();

-- Garantir que o número de bicicletas em reserva é pelo menos 10% das disponíveis
CREATE OR REPLACE FUNCTION check_reserva_bicicletas() RETURNS TRIGGER AS $$
DECLARE
    total_disponiveis INTEGER;
    total_reservadas INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_disponiveis FROM bicicleta WHERE estado = 'livre' AND ativo = TRUE;
    SELECT COUNT(*) INTO total_reservadas FROM bicicleta WHERE estado = 'em reserva' AND ativo = TRUE;

    IF (total_reservadas + 1) > (total_disponiveis * 0.1) THEN
        RAISE EXCEPTION 'Não é possível fazer a reserva, limite de bicicletas reservadas excedido';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_reserva_bicicletas
    BEFORE INSERT ON reserva
    FOR EACH ROW
EXECUTE FUNCTION check_reserva_bicicletas();

ALTER TABLE gps
    ADD COLUMN IF NOT EXISTS bicicleta_id INTEGER UNIQUE,
    ADD FOREIGN KEY (bicicleta_id) REFERENCES bicicleta(id) ON DELETE SET NULL;



CREATE OR REPLACE FUNCTION pode_ser_reservado(
    p_bicicleta_id INTEGER,
    p_data_inicio TIMESTAMP,
    p_data_fim TIMESTAMP
)
    RETURNS BOOLEAN AS $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM reserva
    WHERE bicicleta_id = p_bicicleta_id
      AND (
        (p_data_inicio BETWEEN data_inicio AND data_fim) OR
        (p_data_fim BETWEEN data_inicio AND data_fim) OR
        (data_inicio BETWEEN p_data_inicio AND p_data_fim) OR
        (data_fim BETWEEN p_data_inicio AND p_data_fim)
        );


    IF v_count > 0 THEN
        RETURN FALSE;
    ELSE
        RETURN TRUE;
    END IF;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION adicionar_reserva(
    loja_id INT,
    cliente_id INT,
    bicicleta_id INT,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    valor DOUBLE PRECISION
) RETURNS VOID AS $$
BEGIN
    INSERT INTO reserva (loja_id, cliente_id, bicicleta_id, data_inicio, data_fim, valor)
    VALUES (loja_id, cliente_id, bicicleta_id, data_inicio, data_fim, valor);
END;
$$ LANGUAGE plpgsql;





