package isel.sisinf.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NamedStoredProcedureQuery(
        name = "Reserva.cancelarReserva",
        procedureName = "cancelar_reserva",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class)
        }
)

@NamedStoredProcedureQuery(
        name = "Reserva.createReserva",
        procedureName = "adicionar_reserva",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = java.sql.Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = java.sql.Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class)
})

@NamedQuery(name="Reserva.findByKey",
        query="SELECT r FROM Reserva r WHERE r.numero =:key")
@NamedQuery(name="Reserva.findAll",
        query="SELECT r FROM Reserva r")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "bicicleta_id", nullable = false)
    private Bicicleta bicicleta;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Column(nullable = false)
    private Double valor;

    @Version
    private Long version;

    @Override
    public String toString() {
        return "Reserva{" +
                "numero=" + numero +
                ", loja=" + loja.toString() +
                ", cliente=" + cliente.getNome() +
                ", bicicleta=" + bicicleta.getModelo() +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valor=" + valor +
                ", version=" + version +
                '}';
    }
}
