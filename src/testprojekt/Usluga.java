package testprojekt;

import javafx.scene.control.DatePicker;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "usluga")
public class Usluga implements Serializable{
    @Column(name = "id_uslugi",unique = true)
    @Id
    @GeneratedValue
    private int id_uslugi;

    @Column(name="cena")
    private double cena;

    @Column(name="nazwa_uslugi")
    private String nazwa;

    @Column(name="data_uslugi")
    private Date data_uslugi;

    @Column(name="godzina_uslugi")
    private Time czas;

    @ManyToOne
    @JoinColumn(name="id_klienta", referencedColumnName = "id_klienta")
    private Klient klient;

    @ManyToOne
    @JoinColumn(name="id_fryzjera", referencedColumnName = "id_fryzjera")
    private Fryzjer fryzjer;

    public Usluga() {
    }

    public Usluga(double cena, String nazwa, Date data_uslugi, Time czas) {
        this.cena = cena;
        this.nazwa = nazwa;
        this.data_uslugi = data_uslugi;
        this.czas = czas;
    }

    public int getId_uslugi() {
        return id_uslugi;
    }

    public void setId_uslugi(int id_uslugi) {
        this.id_uslugi = id_uslugi;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Date getData_uslugi() {
        return data_uslugi;
    }

    public void setData_uslugi(Date data_uslugi) {
        this.data_uslugi = data_uslugi;
    }

    public Time getCzas() {
        return czas;
    }

    public void setCzas(Time czas) {
        this.czas = czas;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Fryzjer getFryzjer() {
        return fryzjer;
    }

    public void setFryzjer(Fryzjer fryzjer) {
        this.fryzjer = fryzjer;
    }

    @Override
    public String toString() {
        return getCena() + " " + getData_uslugi().toString() + " " + getCzas().toString() + " " +
                getNazwa() + " " + getKlient().getImie() + " " + getKlient().getNazwisko() + " " + getFryzjer().getImie() + " " + getFryzjer().getNazwisko();
    }
}
