package businessLogic;
 
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.User;
import domain.Admin;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kontaktua;
import domain.Kuota;
import domain.Mezua;
import domain.Mugimendua;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question);		

		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    @WebMethod	
    public boolean erabiltzaileaDago(String eizena) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.erabiltzaileaDago(eizena);
    	dbManager.close();
    	return(emaitza);
    }
    @WebMethod	
    public User pasahitzaZuzenaDa(String erabiltzailea, String pasahitza) {
    	dbManager.open(false);
		User emaitza= dbManager.pasahitzaZuzenaDa(erabiltzailea, pasahitza);
		dbManager.close();
		return (emaitza);
    }
	@WebMethod	
 	public Admin getAdmin (String id) {
		dbManager.open(false);
		Admin erab = dbManager.getAdmin(id);
		dbManager.close();
		return(erab);
	}
    @WebMethod	
    public boolean erregistratu(Erabiltzailea user) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.erregistratu(user);
		dbManager.close();
		return(emaitza);
    }
    @WebMethod	
    public boolean gertaeraSortu (Event event) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.gertaeraSortu(event);
		dbManager.close();
		return(emaitza);
    }
    @WebMethod	
    public boolean kuotakIpini (Question galdera, Kuota kuotaBerria) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.kuotakIpini(galdera, kuotaBerria);
		dbManager.close();
		return(emaitza);
   }
    @WebMethod	
    public boolean diruaSartu(int dirua, Erabiltzailea e) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.diruaSartu(dirua, e);
    	dbManager.close();
    	return(emaitza);
    }
    @WebMethod	
    public Vector<Mugimendua> mugimenduakErakutsi(Erabiltzailea e) {
    	dbManager.open(false);
		Vector<Mugimendua>  mugimenduak = dbManager.mugimenduakErakutsi(e);
		dbManager.close();
		return mugimenduak;
    }
    @WebMethod	
	public void apustuaEzabatu(Apustua apustua) {
		dbManager.open(false);
		dbManager.apustuaEzabatu(apustua);
		dbManager.close();
	}
    @WebMethod	
	public boolean apustuaEgin(Apustua apustua) {
		dbManager.open(false);
		boolean emaitza = dbManager.apustuaEgin(apustua);
		dbManager.close();
		return(emaitza);
	}
    @WebMethod	
	public boolean ezarriEmaitza(Question question, Kuota kuota) {
		dbManager.open(false);
		boolean emaitza = dbManager.ezarriEmaitza(question, kuota);
		dbManager.close();
		return(emaitza);
	}
    @WebMethod	
	public void gertaeraEzabatu (Event ev, String arrazoia) {
		dbManager.open(false);
		dbManager.gertaeraEzabatu(ev, arrazoia);
		dbManager.close();
	}
    @WebMethod	
	public Vector<Apustua> getApustuak(Erabiltzailea erabiltzailea) {
		dbManager.open(false);
		Vector<Apustua> apustuak = dbManager.getApustuak(erabiltzailea);
		dbManager.close();
		return apustuak;
	}
    @WebMethod
    public Kuota getKuota (Apustua ap) {
		dbManager.open(false);
		Kuota ku = dbManager.getKuota(ap);
		dbManager.close();
		return(ku);
	}
    @WebMethod
	public Question getGaldera (Kuota ku) {
		dbManager.open(false);
		Question qu = dbManager.getGaldera(ku);
		dbManager.close();
		return(qu);
	}
    @WebMethod
	public Event getEvent (Question ev) {
		dbManager.open(false);
		Event eve = dbManager.getEvent(ev);
		dbManager.close();
		return(eve);
	}
    @WebMethod
	public Erabiltzailea getErabiltzailea (Apustua ap) {
		dbManager.open(false);
		Erabiltzailea erab = dbManager.getErabiltzailea(ap);
		dbManager.close();
		return(erab);
	}
    @WebMethod
	public Apustua getApustua (Apustua ap) {
		dbManager.open(false);
		Apustua itzuli = dbManager.getApustua(ap);
		dbManager.close();
		return(itzuli);
	}
    @WebMethod
    public List<Apustua> getErabiltzailearenApustuak (Erabiltzailea erab) {
    	dbManager.open(false);
		List<Apustua> itzuli = dbManager.getErabiltzailearenApustuak(erab);
		dbManager.close();
		return(itzuli);
	}
    @WebMethod
	public List<Question> getEventQuestionsFromDB (Event ev) {
    	dbManager.open(false);
		List<Question> itzuli = dbManager.getEventQuestionsFromDB(ev);
		dbManager.close();
		return(itzuli);
	}
    @WebMethod
	public List<Kuota> getQuestionKuotakFromDB (Question q) {
    	dbManager.open(false);
		List<Kuota> itzuli = dbManager.getQuestionKuotakFromDB(q);
		dbManager.close();
		return(itzuli);
	}
    @WebMethod
	public List<Apustua> getKuotenApustuakFromDB (Kuota ku) {
    	dbManager.open(false);
		List<Apustua> itzuli = dbManager.getKuotenApustuakFromDB(ku);
		dbManager.close();
		return(itzuli);
	}
    
    @WebMethod
	public void apustuAnitzaEgin (ApustuAnitza apa, Vector<Apustua> apustuak) {
    	dbManager.open(false);
		dbManager.apustuAnitzaEgin(apa,apustuak);
		dbManager.close();
    }
    
    @WebMethod
    public ApustuAnitza emanApustuAnitza (Integer id) {
    	dbManager.open(false);
		ApustuAnitza anitz = dbManager.getApustuAnitza(id);
		dbManager.close();
		return(anitz);
    }
    @WebMethod
    public void apustuAnitzaEzabatu (ApustuAnitza apustuAnitza) {
    	dbManager.open(false);
		dbManager.apustuAnitzaEzabatu(apustuAnitza);
		dbManager.close();
    }
    @WebMethod
	public Erabiltzailea getErabiltzaileaID (String id) {
    	dbManager.open(false);
		Erabiltzailea erab = dbManager.getErabiltzailea(id);
		dbManager.close();
		return(erab);
    }
    @WebMethod
    public List<Kontaktua> eskuratuKontaktuak (User igorlea) {
    	dbManager.open(false);
    	List<Kontaktua> itzul = dbManager.eskuratuKontaktuak(igorlea);
    	dbManager.close();
    	return(itzul);
    }
    @WebMethod
    public boolean gehituKontaktua (String izena, User igorlea) {
    	dbManager.open(false);
    	boolean itzul = dbManager.gehituKontaktua(izena,igorlea);
    	dbManager.close();
    	return(itzul);
    }
    @WebMethod
	public boolean bidaliMezua (User igorlea, User hartzailea, String mezua, Date data, Kontaktua unekoarenKontaktua) {
    	dbManager.open(false);
    	boolean itzul = dbManager.bidaliMezua(igorlea,hartzailea,mezua,data,unekoarenKontaktua);
    	dbManager.close();
    	return(itzul);
	}
    @WebMethod
	public List<Mezua> eskuratuMezuak (int kontaktuaID, User igorlea, User hartzailea) {
    	dbManager.open(false);
    	List<Mezua> itzul = dbManager.eskuratuMezuak(kontaktuaID, igorlea,hartzailea);
    	dbManager.close();
    	return(itzul);
	}
    @WebMethod
	public List<Mezua> freskatuTxata (int kontaktuaID) {
		dbManager.open(false);
    	List<Mezua> itzul = dbManager.freskatuTxata(kontaktuaID);
    	System.out.println(itzul.size());
    	dbManager.close();
    	return(itzul);
	}
    @WebMethod
    public boolean mezuakIkusiDira (int kontaktuaID) {
		dbManager.open(false);
    	boolean itzul = dbManager.mezuakIkusiDira(kontaktuaID);
    	dbManager.close();
    	return(itzul);
    }
    
    @WebMethod
    public boolean badaukaKontaktua (String igorlea, String hartzailea) {
		dbManager.open(false);
    	boolean itzul = dbManager.badaukaKontaktua(igorlea,hartzailea);
    	dbManager.close();
    	return(itzul);
    }
    
    @WebMethod
    public void cashOutEgin(ApustuAnitza apustuAnitza, double irabaziak, Erabiltzailea erabiltzailea) {
    	dbManager.open(false);
    	dbManager.cashOutEgin(apustuAnitza, irabaziak, erabiltzailea);
    	dbManager.close();
    }
    
    @WebMethod
    public Vector<Apustua> getApustuAnitzenApustuak(ApustuAnitza apustuAnitza) {
    	dbManager.open(false);
		Vector<Apustua> apustuak = dbManager.getApustuAnitzenApustuak(apustuAnitza);
		dbManager.close();
		return apustuak;
    }
}

