INSERT INTO lojas (gestor, morada, telefone, email)
VALUES
    ('João Silva', 'Rua das Flores, 123', '123456789', 'joao.silva@exemplo.com'),
    ('Maria Oliveira', 'Avenida dos Aliados, 456', '987654321', 'maria.oliveira@exemplo.com');

INSERT INTO gps (numero_serie, latitude, longitude, percentagem_bateria)
VALUES
    ('GPS123456', 40.712776, -74.005974, 85.50),
    ('GPS154321', 34.052235, -118.243683, 92.75),
    ('GPS789012', 38.907192, -77.036871, 90.25),
    ('GPS345678', 51.507351, -0.127758, 95.00),
    ('GPS901234', 48.856613, 2.352222, 87.75),
    ('GPS567890', 35.689487, 139.691706, 82.50),
    ('GPS216987', 37.774929, -122.419416, 80.25),
    ('GPS896543', 55.755826, 37.617300, 75.00),
    ('GPS432109', 40.416775, -3.703790, 70.75),
    ('GPS698765', 52.520007, 13.404954, 65.50),
    ('GPS654329', 34.052235, -118.243683, 60.25),
    ('GPS321098', 41.902783, 12.496366, 55.00),
    ('GPS889012', 38.907192, -77.036871, 50.75),
    ('GPS345679', 51.507351, -0.127758, 45.50),
    ('GPS911234', 48.856613, 2.352222, 40.25),
    ('GPS569890', 35.689487, 139.691706, 35.00),
    ('GPS210987', 37.774929, -122.419416, 30.75),
    ('GPS876543', 55.755826, 37.617300, 25.50),
    ('GPS432199', 40.416775, -3.703790, 20.25),
    ('GPS098765', 52.520007, 13.404954, 15.00),
    ('GPS654321', 34.052235, -118.243683, 10.75),
    ('GPS321798', 41.902783, 12.496366, 5.50),
    ('GPS789011', 38.907192, -77.036871, 0.25),
    ('GPS789013', 38.907192, -77.036871, 0.25),
    ('GPS789014', 38.907192, -77.036871, 0.55),
    ('GPS789015', 38.907192, -77.036871, 0.45),
    ('GPS789016', 38.907192, -77.036871, 0.35)
    ;

INSERT INTO bicicletas (tipo, peso, modelo, marca, sistema_mudancas, estado, autonomia, velocidade_maxima, id_gps)
VALUES
    ('classica', 15, 'Modelo A', 'Marca X', 'manual', 'livre', NULL, NULL, 1),
    ('eletrica', 20, 'Modelo B', 'Marca Y', 'automatico', 'livre', 100, 25, 2),
    ('eletrica', 20, 'Modelo C', 'Marca Z', 'automatico', 'livre', 100, 25, 3),
    ('classica', 15, 'Modelo D', 'Marca W', 'manual', 'livre', NULL, NULL, 4),
    ('eletrica', 20, 'Modelo E', 'Marca V', 'automatico', 'livre', 100, 25, 5),
    ('eletrica', 20, 'Modelo F', 'Marca U', 'automatico', 'livre', 100, 25, 6),
    ('classica', 15, 'Modelo G', 'Marca T', 'manual', 'livre', NULL, NULL, 7),
    ('eletrica', 20, 'Modelo H', 'Marca S', 'automatico', 'livre', 100, 25, 8),
    ('eletrica', 20, 'Modelo I', 'Marca R', 'automatico', 'livre', 100, 25, 9),
    ('classica', 15, 'Modelo J', 'Marca Q', 'manual', 'livre', NULL, NULL, 10),
    ('eletrica', 20, 'Modelo K', 'Marca P', 'automatico', 'livre', 100, 25, 11),
    ('eletrica', 20, 'Modelo L', 'Marca O', 'automatico', 'livre', 100, 25, 12),
    ('classica', 15, 'Modelo M', 'Marca N', 'manual', 'livre', NULL, NULL, 13),
    ('eletrica', 20, 'Modelo N', 'Marca M', 'automatico', 'livre', 100, 25, 14),
    ('eletrica', 20, 'Modelo O', 'Marca L', 'automatico', 'livre', 100, 25, 15),
    ('classica', 15, 'Modelo P', 'Marca K', 'manual', 'livre', NULL, NULL, 16),
    ('eletrica', 20, 'Modelo Q', 'Marca J', 'automatico', 'livre', 100, 25, 17),
    ('eletrica', 20, 'Modelo R', 'Marca I', 'automatico', 'livre', 100, 25, 18),
    ('classica', 15, 'Modelo S', 'Marca H', 'manual', 'livre', NULL, NULL, 19),
    ('eletrica', 20, 'Modelo T', 'Marca G', 'automatico', 'livre', 100, 25, 20),
    ('eletrica', 20, 'Modelo U', 'Marca F', 'automatico', 'livre', 100, 25, 21),
    ('classica', 15, 'Modelo V', 'Marca E', 'manual', 'livre', NULL, NULL, 22),
    ('eletrica', 20, 'Modelo W', 'Marca D', 'automatico', 'livre', 100, 25, 23),
    ('eletrica', 20, 'Modelo X', 'Marca C', 'automatico', 'livre', 100, 25, 24),
    ('classica', 15, 'Modelo Y', 'Marca B', 'manual', 'livre', NULL, NULL, 25),
    ('eletrica', 20, 'Modelo Z', 'Marca A', 'automatico', 'livre', 100, 25, 26);

;

INSERT INTO clientes (nome, morada, email, telefone, cc_passaporte, nacionalidade)
VALUES
    ('Ana Costa', 'Rua das Laranjeiras, 789', 'ana.costa@exemplo.com', '123123123', 'CC12345678', 'Portuguesa'),
    ('Pedro Gomes', 'Praça do Comércio, 101', 'pedro.gomes@exemplo.com', '321321321', 'CC87654321', 'Portuguesa');


INSERT INTO reservas (loja_id, cliente_id, bicicleta_id, data_inicio, data_fim, valor)
VALUES
    (1, 1, 1, '2024-06-01 10:00:00', '2024-06-01 18:00:00', 15.00);

-- Insert dummy data into clientes
INSERT INTO clientes (nome, morada, email, telefone, cc_passaporte, nacionalidade)
VALUES
    ('Ana Costa', 'Rua das Laranjeiras, 789', 'ana.costa@exemplo.com', '123123123', 'CC12345678', 'Portuguesa'),
    ('Pedro Gomes', 'Praça do Comércio, 101', 'pedro.gomes@exemplo.com', '321321321', 'CC87654321', 'Portuguesa');




