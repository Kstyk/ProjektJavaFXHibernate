package testprojekt;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "fryzjer")
public class Fryzjer implements Serializable{
    private static final long serialVersionUID = -400025L;

    @Column(name = "id_fryzjera", unique = true)
    @Id
    @GeneratedValue
    private int id_fryzjera;

    @Column(name="imie_fryzjera")
    private String imie;

    @Column(name = "nazwisko_fryzjera")
    private String nazwisko;

    @Column(name="telefon_fryzjera")
    private String telefon;

    @ManyToOne
    @JoinColumn(name="id_oddzialu", referencedColumnName = "oddzial_id")
    private Oddzial oddzial;

    @OneToMany(mappedBy = "fryzjer")
    private List<Usluga> uslugi_fryzjerzy;

    public Fryzjer() {
    }

    public Fryzjer(String imie, String nazwisko, String telefon) {
        setImie(imie);
        setNazwisko(nazwisko);
        setTelefon(telefon);
    }

    public int getId_fryzjera() {
        return id_fryzjera;
    }

    public void setId_fryzjera(int id_fryzjera) {
        this.id_fryzjera = id_fryzjera;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Oddzial getOddzial() {
        return oddzial;
    }

    public void setOddzial(Oddzial oddzial) {
        this.oddzial = oddzial;
    }

    @Override
    public String toString() {
        return getImie() + " " + getNazwisko() + " " + getTelefon() + " " + getOddzial().getNazwa();
    }
}
