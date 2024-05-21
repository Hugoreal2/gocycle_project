INSERT INTO lojas (gestor, morada, telefone, email)
VALUES
    ('João Silva', 'Rua das Flores, 123', '123456789', 'joao.silva@exemplo.com'),
    ('Maria Oliveira', 'Avenida dos Aliados, 456', '987654321', 'maria.oliveira@exemplo.com');

INSERT INTO gps (numero_serie, latitude, longitude, percentagem_bateria)
VALUES
    ('GPS123456', 40.712776, -74.005974, 85.50),
    ('GPS654321', 34.052235, -118.243683, 92.75);

INSERT INTO bicicletas (tipo, peso, modelo, marca, sistema_mudancas, estado, autonomia, velocidade_maxima, id_gps)
VALUES
    ('classica', 15, 'Modelo A', 'Marca X', 'manual', 'livre', NULL, NULL, 1),
    ('eletrica', 20, 'Modelo B', 'Marca Y', 'automatico', 'livre', 100, 25, 2),
    ('eletrica', 20, 'Modelo C', 'Marca Z', 'automatico', 'livre', 100, 25, 2),
    ('classica', 15, 'Modelo D', 'Marca W', 'manual', 'livre', NULL, NULL, 1),
    ('eletrica', 20, 'Modelo E', 'Marca V', 'automatico', 'livre', 100, 25, 2),
    ('eletrica', 20, 'Modelo F', 'Marca U', 'automatico', 'livre', 100, 25, 2)

;


INSERT INTO clientes (nome, morada, email, telefone, cc_passaporte, nacionalidade)
VALUES
    ('Ana Costa', 'Rua das Laranjeiras, 789', 'ana.costa@exemplo.com', '123123123', 'CC12345678', 'Portuguesa'),
    ('Pedro Gomes', 'Praça do Comércio, 101', 'pedro.gomes@exemplo.com', '321321321', 'CC87654321', 'Portuguesa');

INSERT INTO reservas (loja_id, cliente_id, bicicleta_id, data_inicio, data_fim, valor)
VALUES
    (1, 1, 1, '2024-06-01 10:00:00', '2024-06-01 18:00:00', 15.00);

-- Insert dummy data into lojas
INSERT INTO lojas (gestor, morada, telefone, email)
VALUES
    ('João Silva', 'Rua das Flores, 123', '123456789', 'joao.silva@exemplo.com'),
    ('Maria Oliveira', 'Avenida dos Aliados, 456', '987654321', 'maria.oliveira@exemplo.com');

-- Insert dummy data into gps
INSERT INTO gps (numero_serie, latitude, longitude, percentagem_bateria)
VALUES
    ('GPS123456', 40.712776, -74.005974, 85.50),
    ('GPS654321', 34.052235, -118.243683, 92.75);

-- Insert dummy data into bicicletas
INSERT INTO bicicletas (tipo, peso, modelo, marca, sistema_mudancas, estado, autonomia, velocidade_maxima, id_gps)
VALUES
    ('classica', 15, 'Modelo A', 'Marca X', 'manual', 'livre', NULL, NULL, 1),
    ('eletrica', 20, 'Modelo B', 'Marca Y', 'automatico', 'livre', 100, 25, 2);

-- Insert dummy data into clientes
INSERT INTO clientes (nome, morada, email, telefone, cc_passaporte, nacionalidade)
VALUES
    ('Ana Costa', 'Rua das Laranjeiras, 789', 'ana.costa@exemplo.com', '123123123', 'CC12345678', 'Portuguesa'),
    ('Pedro Gomes', 'Praça do Comércio, 101', 'pedro.gomes@exemplo.com', '321321321', 'CC87654321', 'Portuguesa');

-- Insert dummy data into reservas
INSERT INTO reservas (loja_id, cliente_id, bicicleta_id, data_inicio, data_fim, valor)
VALUES
    (1, 1, 1, '2024-06-01 10:00:00', '2024-06-01 18:00:00', 15.00),
    (2, 2, 2, '2024-06-02 09:00:00', '2024-06-02 17:00:00', 20.00);



