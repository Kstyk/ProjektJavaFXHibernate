package testprojekt;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "klient")
public class Klient implements Serializable {
    private static final long serialVersionUID = -400025L;

    @Column(name = "id_klienta", unique = true)
    @Id
    @GeneratedValue
    private int id_klienta;

    @Column(name = "imie_klienta")
    private String imie;

    @Column(name = "nazwisko_klienta")
    private String nazwisko;

    @Column(name="telefon")
    private String telefon;

    @Column(name="plec")
    private Character plec;

    @OneToMany(mappedBy = "klient")
    private List<Usluga> uslugi_klienci;

    public Klient() {
    }

    public Klient(String imie, String nazwisko, String telefon, Character plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.plec = plec;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
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

    public Character getPlec() {
        return plec;
    }

    public void setPlec(Character plec) {
        this.plec = plec;
    }

    @Override
    public String toString() {
        return getImie() + " " + getNazwisko() + " " + getTelefon() + " " + getPlec();
    }
}
