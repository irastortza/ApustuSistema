package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue @XmlID
	private Integer questionNumber;
	private String question;
	private int eventID;
	private boolean emaitzaJarrita;
	private String result;  
	@XmlIDREF
	private Event event;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Kuota> kuotak = new Vector<Kuota> ();

	public Question(){
		super();
	}
	
	public Question(Integer queryNumber, String query, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.event = event;
		this.eventID = event.getEventNumber();
	}
	
	public Question(String query,  Event event) {
		super();
		this.question = query;
		this.event = event;
		this.eventID = event.getEventNumber();
	}
	
	public Question(String query) {
		super();
		this.question = query;

		
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the kuotak
	 */
	public Vector<Kuota> getKuotak() {
		return kuotak;
	}

	/**
	 * @param kuotak the kuotak to set
	 */
	public void setKuotak(Vector<Kuota> kuotak) {
		this.kuotak = kuotak;
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}


	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
		this.eventID = event.getEventNumber();
	}




	public String toString(){
		return questionNumber+";"+question;
	}


	public void addKuota (Kuota kuota) {
		this.kuotak.add(kuota);
	}
	
	public Vector<Kuota> getKuotaBektorea () {
		return(this.kuotak);
	}
	
	public boolean isEmaitzaJarrita() {
		return emaitzaJarrita;
	}

	public void setEmaitzaJarrita(boolean emaitzaJarrita) {
		this.emaitzaJarrita = emaitzaJarrita;
	}

}