package testprojekt;

import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usluga.class)
public abstract class Usluga_ {

	public static volatile SingularAttribute<Usluga, Date> data_uslugi;
	public static volatile SingularAttribute<Usluga, Time> czas;
	public static volatile SingularAttribute<Usluga, Integer> id_uslugi;
	public static volatile SingularAttribute<Usluga, Klient> klient;
	public static volatile SingularAttribute<Usluga, Double> cena;
	public static volatile SingularAttribute<Usluga, Fryzjer> fryzjer;
	public static volatile SingularAttribute<Usluga, String> nazwa;

	public static final String DATA_USLUGI = "data_uslugi";
	public static final String CZAS = "czas";
	public static final String ID_USLUGI = "id_uslugi";
	public static final String KLIENT = "klient";
	public static final String CENA = "cena";
	public static final String FRYZJER = "fryzjer";
	public static final String NAZWA = "nazwa";

}

