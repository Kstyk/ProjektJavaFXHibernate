package testprojekt;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Oddzial.class)
public abstract class Oddzial_ {

	public static volatile SingularAttribute<Oddzial, Adres> adres_oddzialu;
	public static volatile SingularAttribute<Oddzial, Integer> oddzial_id;
	public static volatile ListAttribute<Oddzial, Fryzjer> fryzjerzy;
	public static volatile SingularAttribute<Oddzial, String> nazwa;

	public static final String ADRES_ODDZIALU = "adres_oddzialu";
	public static final String ODDZIAL_ID = "oddzial_id";
	public static final String FRYZJERZY = "fryzjerzy";
	public static final String NAZWA = "nazwa";

}

