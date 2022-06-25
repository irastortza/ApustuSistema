package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.List;

//import domain.Booking;
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

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	
	@WebMethod public boolean erabiltzaileaDago(String erabltzailea);
	
	@WebMethod public User pasahitzaZuzenaDa(String erabiltzailea, String pasahitza);
	
	@WebMethod public boolean erregistratu(Erabiltzailea user);
	
	@WebMethod public boolean gertaeraSortu (Event event);
	
	@WebMethod public boolean kuotakIpini (Question galdera, Kuota kuotaBerria);
	
	@WebMethod public boolean diruaSartu(int dirua, Erabiltzailea e);
	
	@WebMethod public Vector<Mugimendua> mugimenduakErakutsi(Erabiltzailea e);
	
	@WebMethod public void apustuaEzabatu(Apustua apustua);
	
	@WebMethod public boolean apustuaEgin(Apustua apustua);
	
	@WebMethod public boolean ezarriEmaitza(Question question, Kuota kuota);

	@WebMethod public void gertaeraEzabatu (Event ev, String arrazoia);
	
	@WebMethod public Vector<Apustua> getApustuak (Erabiltzailea user);
	
    @WebMethod
    public Kuota getKuota (Apustua ap);
    @WebMethod
	public Question getGaldera (Kuota ku);
    @WebMethod
	public Event getEvent (Question ev);
    @WebMethod
	public Erabiltzailea getErabiltzailea (Apustua ap);
    
    @WebMethod
 	public Apustua getApustua (Apustua ap);
    
    @WebMethod
    public List<Apustua> getErabiltzailearenApustuak (Erabiltzailea erab);
    
    @WebMethod
	public List<Question> getEventQuestionsFromDB (Event ev);
    @WebMethod
	public List<Kuota> getQuestionKuotakFromDB (Question q);
    @WebMethod
	public List<Apustua> getKuotenApustuakFromDB (Kuota ku);
    @WebMethod
	public void apustuAnitzaEgin (ApustuAnitza apa, Vector<Apustua> apustuak);
    
    @WebMethod
    public ApustuAnitza emanApustuAnitza (Integer id);
    
    @WebMethod
    public void apustuAnitzaEzabatu (ApustuAnitza apustuAnitza);
    
    @WebMethod
 	public Erabiltzailea getErabiltzaileaID (String id);
    
    @WebMethod
    public List<Kontaktua> eskuratuKontaktuak (User igorlea);
    
    @WebMethod
    public boolean gehituKontaktua (String izena, User igorlea);
    
    @WebMethod
	public boolean bidaliMezua (User igorlea, User hartzailea, String mezua, Date data, Kontaktua unekoarenKontaktua);
    @WebMethod
	public List<Mezua> eskuratuMezuak (int kontaktuaID, User igorlea, User hartzailea);
    @WebMethod
	public List<Mezua> freskatuTxata (int kontaktuaID);
    @WebMethod
    public boolean mezuakIkusiDira (int kontaktuaID); 
    
    @WebMethod
    public boolean badaukaKontaktua (String igorlea, String hartzailea);
    
    @WebMethod
 	public Admin getAdmin (String id);
    
    @WebMethod
    public void cashOutEgin(ApustuAnitza apustuAnitza, double irabaziak, Erabiltzailea erabiltzailea);
    
    @WebMethod
    public Vector<Apustua> getApustuAnitzenApustuak(ApustuAnitza apustuAnitza);

}
