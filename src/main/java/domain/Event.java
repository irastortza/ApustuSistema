package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer eventNumber;
	private String description;
	private boolean emaitzaGuztiakJarrita;
	private int galderaKopurua;
	private int emaitzakJarritakoGalderak;
	private Date eventDate;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Question> questions=new Vector<Question>();

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	public Event() {
		super();
	}

	public Event(Integer eventNumber, String description,Date eventDate) {
		this.eventNumber = eventNumber;
		this.description = description;
		this.eventDate=eventDate;
		this.galderaKopurua=0;
		this.emaitzakJarritakoGalderak=0;
	}
	
	public Event( String description,Date eventDate) {
		this.description = description;
		this.eventDate=eventDate;
		this.galderaKopurua=0;
		this.emaitzakJarritakoGalderak=0;
	}
	

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	
	public String toString(){
		return eventNumber+";"+description;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String question)  {
        Question q=new Question(question, this);
        questions.add(q);
        this.galderaKopurua++;
        return q;
	}

	
	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean DoesQuestionExists(String question)  {	
		for (Question q:this.getQuestions()){
			if (q.getQuestion().compareTo(question)==0)
				return true;
		}
		return false;
	}
		

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventNumber != other.eventNumber)
			return false;
		return true;
	}

	public boolean isEmaitzaGuztiakJarrita() {
		return emaitzaGuztiakJarrita;
	}

	public void setEmaitzaGuztiakJarrita(boolean emaitzaGuztiakJarrita) {
		this.emaitzaGuztiakJarrita = emaitzaGuztiakJarrita;
	}

	public int getGalderaKopurua() {
		return galderaKopurua;
	}

	public void setGalderaKopurua(int galderaKopurua) {
		this.galderaKopurua = galderaKopurua;
	}

	public int getEmaitzakJarritakoGalderak() {
		return emaitzakJarritakoGalderak;
	}

	public void setEmaitzakJarritakoGalderak(int emaitzakJarritakoGalderak) {
		this.emaitzakJarritakoGalderak = emaitzakJarritakoGalderak;
	}
	
	public void emaitzaBatJarriDa () {
		this.emaitzakJarritakoGalderak++;
		if (this.emaitzakJarritakoGalderak == this.galderaKopurua) this.emaitzaGuztiakJarrita=true;
	}
	
	

}
