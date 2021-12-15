package testprojekt;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Klient.class)
public abstract class Klient_ {

	public static volatile SingularAttribute<Klient, String> imie;
	public static volatile SingularAttribute<Klient, String> nazwisko;
	public static volatile SingularAttribute<Klient, String> telefon;
	public static volatile ListAttribute<Klient, Usluga> uslugi_klienci;
	public static volatile SingularAttribute<Klient, Integer> id_klienta;
	public static volatile SingularAttribute<Klient, Character> plec;

	public static final String IMIE = "imie";
	public static final String NAZWISKO = "nazwisko";
	public static final String TELEFON = "telefon";
	public static final String USLUGI_KLIENCI = "uslugi_klienci";
	public static final String ID_KLIENTA = "id_klienta";
	public static final String PLEC = "plec";

}

