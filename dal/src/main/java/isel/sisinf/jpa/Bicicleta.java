package isel.sisinf.jpa;

import jakarta.persistence.*;

@Entity
@NamedQuery(name="Bicicleta.findByKey",
        query="SELECT c FROM Bicicleta c WHERE c.id =:key")
@NamedQuery(name="Bicicleta.findAll",
        query="SELECT c FROM Bicicleta c")
@NamedStoredProcedureQuery(
        name = "Bicicleta.podeSerReservado",
        procedureName = "pode_ser_reservado",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_bicicleta_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_data_inicio", type = java.sql.Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_data_fim", type = java.sql.Timestamp.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = Boolean.class)
        }
)


public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer peso;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false, name = "sistema_mudancas")
    private String sistemaMudancas;

    @Column(nullable = false)
    private String estado;

    private Integer autonomia;

    @Column(name = "velocidade_maxima")
    private Integer velocidadeMaxima;

    @OneToOne
    @JoinColumn(name = "id_gps", nullable = true)
    private Gps gps;

    @Column(nullable = false)
    private Boolean ativo = true;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSistemaMudancas() {
        return sistemaMudancas;
    }

    public void setSistemaMudancas(String sistemaMudancas) {
        this.sistemaMudancas = sistemaMudancas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(Integer autonomia) {
        this.autonomia = autonomia;
    }

    public Integer getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(Integer velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", peso=" + peso +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", sistemaMudancas='" + sistemaMudancas + '\'' +
                ", estado='" + estado + '\'' +
                ", autonomia=" + autonomia +
                ", velocidadeMaxima=" + velocidadeMaxima +
                ", gps=" + gps.toString() +
                ", ativo=" + ativo +
                '}';
    }
}
