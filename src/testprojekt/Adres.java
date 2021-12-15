package testprojekt;

import org.hibernate.annotations.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "adres_oddzialu")
public class Adres implements Serializable {
    
    private static final long serialVersionUID = -300025L;

    @Column(name = "id_adresu", unique = true)
    @GeneratedValue
    @Id
    private int id_adresu;

    @Column(name = "kod_pocztowy")
    private String kodpocztowy;

    @Column(name = "miasto")
    private String miasto;

    @Column(name="ulica")
    private String ulica;

    @Column(name = "numer_domu")
    private String numer_domu;

    @OneToOne(optional = false, cascade = CascadeType.ALL, mappedBy = "adres_oddzialu")
    private Oddzial oddzial;

    public Adres() {
    }
    
    public Adres(String kodpocztowy, String miasto, String ulica, String numer_domu) {
        this.kodpocztowy = kodpocztowy;
        this.miasto = miasto;
        this.ulica = ulica;
        this.numer_domu = numer_domu;
    }

    public int getId_adresu() {
        return id_adresu;
    }

    public void setId_adresu(int id_adresu) {
        this.id_adresu = id_adresu;
    }

    public String getKodpocztowy() {
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNumer_domu() {
        return numer_domu;
    }

    public void setNumer_domu(String numer_domu) {
        this.numer_domu = numer_domu;
    }

    public Oddzial getOddzial() {
        return oddzial;
    }

    public void setOddzial(Oddzial oddzial) {
        this.oddzial = oddzial;
    }
}
