package isel.sisinf.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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
                '}';
    }
}
