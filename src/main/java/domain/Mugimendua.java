package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Mugimendua implements Serializable {
	@Id @GeneratedValue @XmlID 	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer mugiNumber;
	private String mugiMota;	//Sartu, Apustua, Irabazi, Galdu, Erretiratu
	private double mugiDirua;
	@XmlIDREF
	private Erabiltzailea erabiltzailea;
	
	
	public Mugimendua() {
		super();
	}


	public Mugimendua(String mugiMota, double mugiDirua, Erabiltzailea erabiltzailea) {
		super();
		this.mugiMota = mugiMota;
		if (mugiMota.contains("Apustua") || mugiMota.contains("Galdu") || mugiMota.contains("galdu") || mugiMota.contains("Apustu anitza egin")) this.mugiDirua=-mugiDirua;
		else this.mugiDirua=mugiDirua;
		this.erabiltzailea = erabiltzailea;
	}


	/**
	 * @return the mugiNumber
	 */
	public int getMugiNumber() {
		return mugiNumber;
	}


	/**
	 * @param mugiNumber the mugiNumber to set
	 */
	public void setMugiNumber(int mugiNumber) {
		this.mugiNumber = mugiNumber;
	}


	/**
	 * @return the mugiMota
	 */
	public String getMugiMota() {
		return mugiMota;
	}


	/**
	 * @param mugiMota the mugiMota to set
	 */
	public void setMugiMota(String mugiMota) {
		this.mugiMota = mugiMota;
	}


	/**
	 * @return the mugiDirua
	 */
	public double getMugiDirua() {
		return mugiDirua;
	}


	/**
	 * @param mugiDirua the mugiDirua to set
	 */
	public void setMugiDirua(int mugiDirua) {
		this.mugiDirua = mugiDirua;
	}


	/**
	 * @return the erabiltzailea
	 */
	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}


	/**
	 * @param erabiltzailea the erabiltzailea to set
	 */
	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	
	
	
}
