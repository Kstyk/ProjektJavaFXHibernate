package testprojekt;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Adres.class)
public abstract class Adres_ {

	public static volatile SingularAttribute<Adres, String> numer_domu;
	public static volatile SingularAttribute<Adres, String> ulica;
	public static volatile SingularAttribute<Adres, Oddzial> oddzial;
	public static volatile SingularAttribute<Adres, String> miasto;
	public static volatile SingularAttribute<Adres, String> kodpocztowy;
	public static volatile SingularAttribute<Adres, Integer> id_adresu;

	public static final String NUMER_DOMU = "numer_domu";
	public static final String ULICA = "ulica";
	public static final String ODDZIAL = "oddzial";
	public static final String MIASTO = "miasto";
	public static final String KODPOCZTOWY = "kodpocztowy";
	public static final String ID_ADRESU = "id_adresu";

}

