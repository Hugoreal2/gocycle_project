package isel.sisinf.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Gps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @Column(nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double percentagemBateria;

    @ManyToOne
    @JoinColumn(name = "bicicleta_id", nullable = true)
    private Bicicleta bicicleta;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPercentagemBateria() {
        return percentagemBateria;
    }

    public void setPercentagemBateria(Double percentagemBateria) {
        this.percentagemBateria = percentagemBateria;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }
}
