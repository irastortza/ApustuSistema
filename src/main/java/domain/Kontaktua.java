package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Kontaktua implements Serializable {
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue @XmlID
	private Integer id;
	private String izena;
	private String jasotzailea;
	private Date azkenMezuaData;
	private User zein;
	private User zeinena;
	private int mezuBerriak;
	private String azkenMezua;
	
	public Kontaktua () {
		super ();
	}
	
	public Kontaktua (User zein, User zeinena) {
		this.zein=zein;
		this.zeinena=zeinena;
		this.izena=zeinena.getEizena();
		this.jasotzailea=zein.getEizena();
		this.azkenMezuaData=null;
		this.mezuBerriak=0;
	}

	public User getZein() {
		return zein;
	}

	public void setZein(User zein) {
		this.zein = zein;
	}

	public User getZeinena() {
		return zeinena;
	}

	public void setZeinena(User zeinena) {
		this.zeinena = zeinena;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAzkenMezuaData() {
		return azkenMezuaData;
	}

	public void setAzkenMezuaData(Date azkenMezuaData) {
		this.azkenMezuaData = azkenMezuaData;
	}

	public int getMezuBerriak() {
		return mezuBerriak;
	}

	public void setMezuBerriak(int mezuBerriak) {
		this.mezuBerriak = mezuBerriak;
	}

	public String getJasotzailea() {
		return jasotzailea;
	}

	public void setJasotzailea(String jasotzailea) {
		this.jasotzailea = jasotzailea;
	}

	public String getAzkenMezua() {
		return azkenMezua;
	}

	public void setAzkenMezua(String azkenMezua) {
		this.azkenMezua = azkenMezua;
	}
	
	
	
	
}
