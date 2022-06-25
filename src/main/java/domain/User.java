package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.*;
@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable{
	private String izena_abizenak;
	@Id @XmlID
	private String eizena;
	private String pasahitza;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<User> kontaktuak;
	
	public User () {
		super();
	}
	
	public User (String izena_abizenak, String eizena, String pasahitza) {
		this.izena_abizenak=izena_abizenak;
		this.eizena=eizena;
		this.pasahitza=pasahitza;
		this.kontaktuak=new Vector<User> ();
	}
	
	
	public String getIzena_abizenak() {
		return izena_abizenak;
	}
	public void setIzena_abizenak(String izena_abizenak) {
		this.izena_abizenak = izena_abizenak;
	}
	public String getEizena() {
		return eizena;
	}
	public void setEizena(String eizena) {
		this.eizena = eizena;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	public Vector<User> getKontaktuak() {
		return kontaktuak;
	}
	public void setKontaktuak(Vector<User> kontaktuak) {
		this.kontaktuak = kontaktuak;
	}
	

}
