package dataAccess;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kontaktua;
import domain.Kuota;
import domain.Mezua;
import domain.Mugimendua;
import domain.Question;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;

	ConfigXML c=ConfigXML.getInstance();

	
     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		
	}

	public DataAccess()  {	
		 this(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?");
				q2=ev1.addQuestion("¿Quién meterá el primer gol?");
				q3=ev11.addQuestion("¿Quién ganará el partido?");
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?");
				q5=ev17.addQuestion("¿Quién ganará el partido?");
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?");
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?");
				q2=ev1.addQuestion("Who will score first?");
				q3=ev11.addQuestion("Who will win the match?");
				q4=ev11.addQuestion("How many goals will be scored in the match?");
				q5=ev17.addQuestion("Who will win the match?");
				q6=ev17.addQuestion("Will there be goals in the first half?");
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?");
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?");
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?");
				q4=ev11.addQuestion("Zenbat gol sartuko dira?");
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?");
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?");
				
			}
			
			Admin e1 = new Admin("a", "a","a"); //admin
			Erabiltzailea e2 = new Erabiltzailea("s", "s", 1 ,"s"); //erabiltzailea
			db.persist(e1);
			db.persist(e2);
			
			Erabiltzailea e3 = new Erabiltzailea("s1", "s1", 1 ,"s1"); //erabiltzailea
			db.persist(e3);
			
			Mugimendua m1 = new Mugimendua("Sartu", 100, e2);
			Mugimendua m2 = new Mugimendua("Apostatu", 10, e2);
			Mugimendua m3 = new Mugimendua("Irabazi", 20, e2);

			db.persist(m3);
			db.persist(m2);
			db.persist(m1);
			
			Vector<Mugimendua> m = new Vector<Mugimendua>();
			m.add(m3);
			m.add(m2);
			m.add(m1);
			e2.setMugimenduak(m);
			
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 
	
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question);
			//db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return q;
		
	}
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}
	

public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	
}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean erabiltzaileaDago(String eizena) {
		User erabiltzailea;
		erabiltzailea = db.find(User.class, eizena);
		if (erabiltzailea==null) return false; else return true;	
	}
	
	public User pasahitzaZuzenaDa(String erabiltzailea, String pasahitza) {
		User er;
		er= db.find(User.class, erabiltzailea);
		if (er != null && er.getPasahitza().equals(pasahitza)) return(er);
		return(null);
	}
	
	public boolean erregistratu(Erabiltzailea user) {
		if (db.find(Erabiltzailea.class,user.getEizena()) != null) return(false);
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();
		return(true);
	}
	
	public boolean gertaeraSortu (Event event) {
		TypedQuery<Event> query = db.createQuery("SELECT p FROM Event p WHERE p.eventDate=?2 AND p.description=?1",Event.class);
		query.setParameter(1, event.getDescription());
		query.setParameter(2, event.getEventDate());
		System.out.println(event.getDescription() + event.getEventDate() + query.getResultList().toString());
		if (!query.getResultList().isEmpty()) return(false);
		db.getTransaction().begin();
		db.persist(event);
		db.getTransaction().commit();
		return(true);
	}
	
	public boolean kuotakIpini(Question galdera, Kuota kuotaBerria) {
		if (galdera.getKuotaBektorea().indexOf(kuotaBerria) != -1) galdera.getKuotaBektorea().get(galdera.getKuotaBektorea().indexOf(kuotaBerria)).setKuota(kuotaBerria.getKuota());
		else galdera.addKuota(kuotaBerria);
		galdera=db.find(Question.class,galdera.getQuestionNumber());
		if (galdera == null) return(false);
		db.getTransaction().begin();
		if (galdera.getKuotaBektorea().indexOf(kuotaBerria) != -1) galdera.getKuotaBektorea().get(galdera.getKuotaBektorea().indexOf(kuotaBerria)).setKuota(kuotaBerria.getKuota());
		else galdera.addKuota(kuotaBerria);
		db.getTransaction().commit();
		return(true);
	}
	
	public boolean diruaSartu(int dirua, Erabiltzailea e) {
		Erabiltzailea erabiltzailea = db.find(Erabiltzailea.class, e);
		if (erabiltzailea == null) return false;
		db.getTransaction().begin();
		erabiltzailea.diruaGehitu(dirua);
		Mugimendua m = new Mugimendua("Sartu", dirua, erabiltzailea);
		db.persist(m);
		db.getTransaction().commit();
		return true;
	}
	
	public Vector<Mugimendua> mugimenduakErakutsi(Erabiltzailea e) {
		System.out.println(">> DataAccess: mugimenduakErakutsi");
		Vector<Mugimendua> res = new Vector<Mugimendua>();	
		TypedQuery<Mugimendua> query = db.createQuery("SELECT p FROM Mugimendua p WHERE p.erabiltzailea=?1", Mugimendua.class);   
		query.setParameter(1, e);
		List<Mugimendua> mugimenduak = query.getResultList();
	 	 for (Mugimendua m : mugimenduak){
	 	   System.out.println(m.toString());		 
		   res.add(m);
		  }
	 	return res;
	}
	
	public void apustuaEzabatu (Apustua apustua) {
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
		query.setParameter(1, apustua.getApustuZenbakia());
		query.executeUpdate();
		db.getTransaction().commit();
		Erabiltzailea erab = db.find(Erabiltzailea.class, apustua.getErabID());
		db.getTransaction().begin();
		erab.diruaGehitu(apustua.getApostatutakoDirua());
		erab.getApustuak().remove(apustua);
		erab.getMugimenduak().add(new Mugimendua("Erretiratu",apustua.getApostatutakoDirua(),erab));
		db.getTransaction().commit();
	}
	
	private void apustuaEzabatuIsilik (Apustua apustua, Erabiltzailea erab) {
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
		query.setParameter(1, apustua.getApustuZenbakia());
		query.executeUpdate();
		db.getTransaction().commit();
		db.getTransaction().begin();
		erab.diruaGehitu(apustua.getApostatutakoDirua());
		erab.getApustuak().remove(apustua);
		db.getTransaction().commit();
	}
	
	public void apustuAnitzaEzabatu (ApustuAnitza apustuAnitza) {
		TypedQuery<Apustua> query = db.createQuery("SELECT p FROM Apustua p WHERE p.apustuAnitzaID=?1",Apustua.class);
		query.setParameter(1, apustuAnitza.getApustuAnitzNumber());
		List<Apustua> apustuak = query.getResultList();
		Erabiltzailea erab = db.find(Erabiltzailea.class, apustuak.get(0).getErabID());
		for (Apustua ap: apustuak) apustuaEzabatuIsilik(ap,erab);
		db.getTransaction().begin();
		erab.getMugimenduak().add(new Mugimendua("Erretiratu apustu anitzetik",apustuAnitza.getSartutakoDirua(),erab));
		erab.diruaGehitu(apustuAnitza.getSartutakoDirua());
		Query query2 = db.createQuery("DELETE FROM ApustuAnitza a WHERE a.apustuAnitzNumber=?1");
		query2.setParameter(1, apustuAnitza.getApustuAnitzNumber());
		query2.executeUpdate();
		db.getTransaction().commit();
	}
	
	public boolean apustuaEgin(Apustua apustua) {
		db.getTransaction().begin();
		db.persist(apustua);
		db.getTransaction().commit();
		Erabiltzailea erab = db.find(Erabiltzailea.class, this.getErabiltzailea(apustua));
		Kuota ku = db.find(Kuota.class, apustua.getKuotaID());
		Question q = db.find(Question.class, ku.getQuestionID());
		Event ev = db.find(Event.class, q.getEventID());
		db.getTransaction().begin();
		ku.getApustuak().add(apustua);
		erab.getApustuak().add(apustua);
		erab.getMugimenduak().add(new Mugimendua("Apustua "+ev.getDescription()+", "+q.getQuestion()+", "+ku.getDeskribapena(), apustua.getApostatutakoDirua(),erab));
		erab.diruaKendu(apustua.getApostatutakoDirua());
		db.getTransaction().commit();
		return true;
	}
	
	public boolean ezarriEmaitza(Question question, Kuota irabazlea) {
		Question qu = db.find(Question.class, question);
		db.getTransaction().begin();
		for (Kuota ku: qu.getKuotak()) {
			if (ku.getKuotaNumber() == irabazlea.getKuotaNumber()) {
				for (Apustua ap: ku.getApustuak()) {
					if (ap.getErabID() == null) continue;
					if (ap.getApustuAnitzZenbakia() == -1) {
						Erabiltzailea erab = this.getErabiltzailea(ap);
						erab.diruaGehitu(ap.getApostatutakoDirua());
						db.persist(new Mugimendua("Irabazi",ap.getApostatutakoDirua(),erab));
						Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
						query.setParameter(1, ap.getApustuZenbakia());
						query.executeUpdate();
					}
					else {
						ApustuAnitza apus = this.getApustuAnitza(ap.getApustuAnitzZenbakia());
						apus.apustuBatIrabaziDu();
						if (apus.irabaziDu()) {
							Erabiltzailea erab = this.getErabiltzailea(ap);
							erab.diruaGehitu(apus.getIrabaziak());
							db.persist(new Mugimendua("Apustu anitza irabazi",apus.getIrabaziak(),erab));
							Query query = db.createQuery("DELETE FROM ApustuAnitza a WHERE a.apustuAnitzNumber=?1");
							query.setParameter(1, apus.getApustuAnitzNumber());
							query.executeUpdate();
						}
					}
					
				}
			}
			else {
				for (Apustua ap: ku.getApustuak()) {
					if (ap.getErabID() == null) continue;
					Erabiltzailea erab = this.getErabiltzailea(ap);
					if (ap.getApustuAnitzZenbakia() == -1) {
						db.persist(new Mugimendua("Galdu",ap.getApostatutakoDirua(),erab));
						Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
						query.setParameter(1, ap.getApustuZenbakia());
						query.executeUpdate();
					}
					else {
						ApustuAnitza apus = this.getApustuAnitza(ap.getApustuAnitzZenbakia());
						if (apus.isAktibo()) {
							apus.apustuBatGalduDu();
							db.persist(new Mugimendua("Apustu anitza galdu",apus.getSartutakoDirua(),erab));
							Query query = db.createQuery("DELETE FROM ApustuAnitza a WHERE a.apustuAnitzNumber=?1");
							query.setParameter(1, apus.getApustuAnitzNumber());
							query.executeUpdate();
						}
					}
					
				}
			}
		}
		qu.setEmaitzaJarrita(true);
		Event ev = db.find(Event.class, qu.getEventID());
		ev.emaitzaBatJarriDa();
		db.getTransaction().commit();
		return true;
		
	}
	
	/*
	 //HEMEN BERRIRO METODOA EGITEN HARI NAIZ 0-TIK HASIZ
	public boolean ezarriEmaitza(Question question, Kuota kuota, Erabiltzailea erabiltzailea) {
		db.getTransaction().begin();
		//ERABILTZAILEA, GALGERA, KUOTA
		Erabiltzailea erab = db.find(Erabiltzailea.class, erabiltzailea);
		Question ga = db.find(Question.class, question);
		Kuota ku = db.find(Kuota.class, kuota);
		
		//GALDERAREN KUOTA BAKOITZA TRATATU
		//GALDERA_KUOTA KUOTA_IRABAZLEAREN BERDINA BADA
		
		
				
				
		
		return true;
	}
	*/
	
	public void gertaeraEzabatu (Event ev, String arrazoia) {
		db.getTransaction().begin();
		for (Question q: this.getEventQuestionsFromDB(ev)) {
			for (Kuota ku: this.getQuestionKuotakFromDB(q)) {
				for (Apustua ap: this.getKuotenApustuakFromDB(ku)) {
					if (ap.getErabID() == null) continue;
					Erabiltzailea erab = this.getErabiltzailea(ap);
					erab.diruaGehitu(ap.getApostatutakoDirua());
					erab.getApustuak().remove(ap);
					if (ap.getApustuAnitzZenbakia() != -1) {
						ApustuAnitza anitz = this.getApustuAnitza(ap.getApustuAnitzZenbakia());
						double irabaziBerria = anitz.getIrabaziak()-(anitz.getIrabaziak()/anitz.getApustuKopurua());
						if (anitz.getFaltan() != 1) {
							anitz.setFaltan(anitz.getFaltan()-1);
							anitz.setIrabaziak(irabaziBerria);
						}
						else {
							erab.diruaGehitu(irabaziBerria);
							db.persist(new Mugimendua("Apustu anitza irabazi, gertaera bat(zuk) bertan behera",irabaziBerria,erab));
							Query query = db.createQuery("DELETE FROM ApustuAnitza a WHERE a.apustuAnitzNumber=?1");
							query.setParameter(1, anitz.getApustuAnitzNumber());
							query.executeUpdate();
						}
					}
					db.persist(new Mugimendua("Itzulpena: " + arrazoia, ap.getApostatutakoDirua(),erab));
					Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
					query.setParameter(1, ap.getApustuZenbakia());
					query.executeUpdate();
				}
				Query query = db.createQuery("DELETE FROM Kuota a WHERE a.kuotaNumber=?1");
				query.setParameter(1, ku.getKuotaNumber());
				query.executeUpdate();
			}
			Query query = db.createQuery("DELETE FROM Question a WHERE a.questionNumber=?1");
			query.setParameter(1, q.getQuestionNumber());
			query.executeUpdate();
		}
		Query query = db.createQuery("DELETE FROM Event a WHERE a.eventNumber=?1");
		query.setParameter(1, ev.getEventNumber());
		query.executeUpdate();
		db.getTransaction().commit();
	}
	
	
	public Vector<Apustua> getApustuak (Erabiltzailea e) {
		System.out.println(">> DataAccess: getApustuak");
		Vector<Apustua> res = new Vector<Apustua>();	
		TypedQuery<Apustua> query = db.createQuery("SELECT p FROM Apustua p WHERE p.erabiltzailea=?1", Apustua.class);   
		query.setParameter(1, e);
		List<Apustua> apustuak = query.getResultList();
	 	 for (Apustua m : apustuak){
	 	   System.out.println(m.toString());		 
		   res.add(m);
		  }
	 	return res;
	}
	
	public Apustua getApustua (Apustua ap) {
		return(db.find(Apustua.class, ap));
	}
	
	public Kuota getKuota (Apustua ap) {
		return(db.find(Kuota.class, ap.getKuotaID()));
	}
	
	public Question getGaldera (Kuota ku) {
		return(db.find(Question.class, ku.getQuestionID()));
	}
	
	public Event getEvent (Question ev) {
		return(db.find(Event.class, ev.getEventID()));
	}
	
	public Erabiltzailea getErabiltzailea (Apustua ap) {
		return(db.find(Erabiltzailea.class, ap.getErabID()));
	}
	
	public List<Apustua> getErabiltzailearenApustuak (Erabiltzailea erab) {
		TypedQuery<Apustua> query = db.createQuery("SELECT p FROM Apustua p WHERE p.erabID=?1", Apustua.class);   
		query.setParameter(1, erab.getEizena());
		List<Apustua> apustuak = query.getResultList();
		return(apustuak);
	}
	
	public List<Question> getEventQuestionsFromDB (Event ev) {
		TypedQuery<Question> query = db.createQuery("SELECT p FROM Question p WHERE p.eventID=?1", Question.class);   
		query.setParameter(1, ev.getEventNumber());
		List<Question> galderak = query.getResultList();
		return(galderak);
	}
	
	public List<Kuota> getQuestionKuotakFromDB (Question q) {
		TypedQuery<Kuota> query = db.createQuery("SELECT p FROM Kuota p WHERE p.questionID=?1", Kuota.class);   
		query.setParameter(1, q.getQuestionNumber());
		List<Kuota> kuotak = query.getResultList();
		return(kuotak);
	}
	
	public List<Apustua> getKuotenApustuakFromDB (Kuota ku) {
		TypedQuery<Apustua> query = db.createQuery("SELECT p FROM Apustua p WHERE p.kuotaID=?1", Apustua.class);   
		query.setParameter(1, ku.getKuotaNumber());
		List<Apustua> apustuak = query.getResultList();
		return(apustuak);
	}
	
	public void apustuAnitzaEgin (ApustuAnitza apa, Vector<Apustua> apustuak) {
		db.getTransaction().begin();
		db.persist(apa);
		db.getTransaction().commit();
		db.getTransaction().begin();
		ApustuAnitza apustuA = db.find(ApustuAnitza.class,apa);
		Erabiltzailea erab = db.find(Erabiltzailea.class, this.getErabiltzailea(apustuak.firstElement()));
		erab.getMugimenduak().add(new Mugimendua("Apustu anitza egin:", apa.getSartutakoDirua(),erab));
		for (Apustua apus: apustuak) {
			apus.setApustuAnitzaID(apustuA.getApustuAnitzNumber());
			//APUSTUAEGIN-en BLAIOKIDEA, bi transakzio ezin egin aldi berean. Dena batean bilduta eraginkorrago, gainera.
			db.persist(apus);
			Kuota ku = db.find(Kuota.class, apus.getKuotaID());
			Question q = db.find(Question.class, ku.getQuestionID());
			Event ev = db.find(Event.class, q.getEventID());
			ku.getApustuak().add(apus);
			erab.getApustuak().add(apus);
			erab.getMugimenduak().add(new Mugimendua("---> Apustua "+ev.getDescription()+", "+q.getQuestion()+", "+ku.getDeskribapena(), apus.getApostatutakoDirua(),erab));
			///////////////////////////////////////////////////////
		}
		erab.diruaKendu(apa.getSartutakoDirua());
		db.getTransaction().commit();
	}
	
	public ApustuAnitza getApustuAnitza (int id) {
		return(db.find(ApustuAnitza.class, id));
	}
	
	public Erabiltzailea getErabiltzailea (String id) {
		return(db.find(Erabiltzailea.class, id));
	}
	
	public boolean bidaliMezua (User igorlea, User hartzailea, String mezua, Date data, Kontaktua unekoarenKontaktua) {
		Mezua mezu = new Mezua(mezua,igorlea,hartzailea,data);
		Kontaktua k;
		db.getTransaction().begin();
		Kontaktua unekoa = db.find(Kontaktua.class, unekoarenKontaktua);
		db.persist(mezu);
		TypedQuery<Kontaktua> query = db.createQuery("SELECT p FROM Kontaktua p WHERE (p.izena = ?1 AND p.jasotzailea=?2)", Kontaktua.class);
		query.setParameter(2, igorlea.getEizena());
		query.setParameter(1, hartzailea.getEizena());
		List<Kontaktua> lista = query.getResultList();
		if (lista.isEmpty()) {
			k = new Kontaktua(igorlea,hartzailea);
			db.persist(k);
		}
		else k = lista.get(0);
		k.setMezuBerriak(k.getMezuBerriak()+1);
		k.setAzkenMezua(mezua);
		unekoa.setAzkenMezua(mezua);
		//hartzailea.getKontaktuak().add(igorlea);
		db.getTransaction().commit();
		return(true);
		//Erabiltzailea erab1 = db.find(Erabiltzailea.class, igorlea.getEizena());
		//Erabiltzailea erab2 = db.find(Erabiltzailea.class, hartzailea.getEizena());
	}
	
	public List<Mezua> eskuratuMezuak (int kontaktuaID, User igorlea, User hartzailea) {
		TypedQuery<Mezua> query = db.createQuery("SELECT p FROM Mezua p WHERE (p.igorleID = ?1 AND p.hartzaileID=?2) OR (p.igorleID = ?2 AND p.hartzaileID = ?1)", Mezua.class);
		query.setParameter(1, igorlea.getEizena());
		query.setParameter(2, hartzailea.getEizena());
		List<Mezua> lista = query.getResultList();
		if (lista.size() == 0) return(lista);
		db.getTransaction().begin();
		Kontaktua k = db.find(Kontaktua.class, kontaktuaID);
		k.setAzkenMezuaData(lista.get(lista.size()-1).getData());
		db.getTransaction().commit();
		return(lista);
	}
	
	public List<Kontaktua> eskuratuKontaktuak (User igorlea) {
		TypedQuery<Kontaktua> query = db.createQuery("SELECT p FROM Kontaktua p WHERE p.izena = ?1", Kontaktua.class);
		query.setParameter(1, igorlea.getEizena());
		List<Kontaktua> lista = query.getResultList();
		return(lista);
	}
	
	public boolean gehituKontaktua (String izena, User igorlea) {
		User itzul;
		itzul = db.find(Erabiltzailea.class, izena);
		if (itzul == null) itzul = db.find(Admin.class, izena);
		if (itzul == null) return(false);
		db.getTransaction().begin();
		//igorlea.getKontaktuak().add(itzul);
		Kontaktua k = new Kontaktua(itzul,igorlea);
		db.persist(k);
		db.getTransaction().commit();
		return(true);
	}
	
	public List<Mezua> freskatuTxata (int kontaktuaID) {
		Kontaktua kontaktua = db.find(Kontaktua.class, kontaktuaID);
		TypedQuery<Mezua> query;
		if (kontaktua.getAzkenMezuaData() == null) query = db.createQuery("SELECT p FROM Mezua p WHERE ((p.igorleID=?2 AND p.hartzaileID=?3) OR (p.igorleID = ?3 AND p.hartzaileID = ?2))", Mezua.class);
		else query = db.createQuery("SELECT p FROM Mezua p WHERE p.data > ?1 AND ((p.igorleID=?2 AND p.hartzaileID=?3) OR (p.igorleID = ?3 AND p.hartzaileID = ?2))", Mezua.class);
		query.setParameter(1, kontaktua.getAzkenMezuaData());
		query.setParameter(2, kontaktua.getZeinena().getEizena());
		query.setParameter(3, kontaktua.getZein().getEizena());
		List<Mezua> lista = query.getResultList();
		if (lista.size() == 0) return(lista);
		db.getTransaction().begin();
		kontaktua.setAzkenMezuaData(lista.get(lista.size()-1).getData());
		db.getTransaction().commit();
		return(lista);
	}
	
	public boolean mezuakIkusiDira (int kontaktuaID) {
		Kontaktua k = db.find(Kontaktua.class, kontaktuaID);
		db.getTransaction().begin();
		k.setMezuBerriak(0);
		db.getTransaction().commit();
		return(true);
	}
	
	public boolean badaukaKontaktua (String igorlea, String hartzailea) {
		TypedQuery<Kontaktua> query = db.createQuery("SELECT p FROM Kontaktua p WHERE p.izena = ?1 AND p.jasotzailea=?2", Kontaktua.class);
		query.setParameter(1, igorlea);
		query.setParameter(2, hartzailea);
		return(query.getResultList().size() > 0);
	}
	
 	public Admin getAdmin (String id) {
 		return(db.find(Admin.class, id));
	}
 	
	public void cashOutEgin(ApustuAnitza apustuAnitza, double irabaziak, Erabiltzailea erab) {
		Erabiltzailea erabiltzailea = db.find(Erabiltzailea.class, erab.getEizena());
		ApustuAnitza apus = this.getApustuAnitza(apustuAnitza.getApustuAnitzNumber());
		Vector<Apustua> apustuak = this.getApustuAnitzenApustuak(apustuAnitza);
		db.getTransaction().begin();
		erabiltzailea.diruaGehitu(irabaziak);
		for (Apustua ezabatuApustua: apustuak) {
			Query query = db.createQuery("DELETE FROM Apustua a WHERE a.apustuZenbakia=?1");
			query.setParameter(1, ezabatuApustua.getApustuZenbakia());
			query.executeUpdate();
		}
		Query query = db.createQuery("DELETE FROM ApustuAnitza a WHERE a.apustuAnitzNumber=?1");
		query.setParameter(1, apus.getApustuAnitzNumber());
		query.executeUpdate();
		erabiltzailea.getMugimenduak().add(new Mugimendua("Cash out egin",irabaziak,erabiltzailea));
		db.getTransaction().commit();
	}
	
    public Vector<Apustua> getApustuAnitzenApustuak(ApustuAnitza apustuAnitza) {
    	System.out.println(">> DataAccess: getApustuak");
		Vector<Apustua> res = new Vector<Apustua>();	
		TypedQuery<Apustua> query = db.createQuery("SELECT p FROM Apustua p WHERE p.apustuAnitzaID=?1", Apustua.class);
		query.setParameter(1, apustuAnitza.getApustuAnitzNumber());
		List<Apustua> lista = query.getResultList();
	 	 for (Apustua m : lista){
	 	   System.out.println(m.toString());		 
		   res.add(m);
		  }
	 	return res;
    }
}






























