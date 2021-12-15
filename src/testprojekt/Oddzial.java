package testprojekt;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import java.util.List;

@Entity
@Table(name = "oddzialy")
public class Oddzial implements Serializable {
    private static final long serialVersionUID =-310025L;

    @Column(name = "oddzial_id", unique = true)
    @Id
    @GeneratedValue
    private int oddzial_id;

    @Column(name = "nazwa")
    private String nazwa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_adresu", unique = true, referencedColumnName = "id_adresu")
    private Adres adres_oddzialu;

    @OneToMany(mappedBy = "oddzial")
    private List<Fryzjer> fryzjerzy;

    public Oddzial(String nazwa, Adres adress) {
        this.nazwa = nazwa;
        this.adres_oddzialu = adress;
    }

    public Oddzial() {
    }

    public int getOddzial_id() {
        return oddzial_id;
    }

    public void setOddzial_id(int oddzial_id) {
        this.oddzial_id = oddzial_id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Adres getAdres_oddzialu() {
        return adres_oddzialu;
    }

    public void setAdres_oddzialu(Adres adres_oddzialu) {
        this.adres_oddzialu = adres_oddzialu;
    }

    public List<Fryzjer> getFryzjerzy() {
        return fryzjerzy;
    }

    public void setFryzjerzy(List<Fryzjer> fryzjerzy) {
        this.fryzjerzy = fryzjerzy;
    }

    @Override
    public String toString() {
        return getNazwa() + " " + getAdres_oddzialu().getKodpocztowy() + " " + getAdres_oddzialu().getNumer_domu() + " " +
                getAdres_oddzialu().getMiasto() + " " + getAdres_oddzialu().getUlica();
    }
}
