package testprojekt;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fryzjer.class)
public abstract class Fryzjer_ {

	public static volatile SingularAttribute<Fryzjer, String> imie;
	public static volatile SingularAttribute<Fryzjer, String> nazwisko;
	public static volatile ListAttribute<Fryzjer, Usluga> uslugi_fryzjerzy;
	public static volatile SingularAttribute<Fryzjer, String> telefon;
	public static volatile SingularAttribute<Fryzjer, Oddzial> oddzial;
	public static volatile SingularAttribute<Fryzjer, Integer> id_fryzjera;

	public static final String IMIE = "imie";
	public static final String NAZWISKO = "nazwisko";
	public static final String USLUGI_FRYZJERZY = "uslugi_fryzjerzy";
	public static final String TELEFON = "telefon";
	public static final String ODDZIAL = "oddzial";
	public static final String ID_FRYZJERA = "id_fryzjera";

}

