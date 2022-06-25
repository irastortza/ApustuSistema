package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Mezua implements Serializable {
	@XmlID @Id @GeneratedValue 	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer mezuNumber;
	private Date data;
	private String testua;
	@XmlIDREF 
	private User igorlea;
	@XmlIDREF 
	private User hartzailea;
	private String igorleID;
	private String hartzaileID;
	
	public Mezua () {
		this.testua=null;
		this.igorlea=null;
		this.hartzailea=null;
	}
	
	public Mezua (String testua, User igorlea, User hartzailea, Date data) {
		this.testua=testua;
		this.igorlea=igorlea;
		this.hartzailea=hartzailea;
		this.data=data;
		this.igorleID=igorlea.getEizena();
		this.hartzaileID=hartzailea.getEizena();
	}

	public int getMezuNumber() {
		return mezuNumber;
	}

	public void setMezuNumber(int mezuNumber) {
		this.mezuNumber = mezuNumber;
	}

	public String getTestua() {
		return testua;
	}

	public void setTestua(String testua) {
		this.testua = testua;
	}

	public User getIgorlea() {
		return igorlea;
	}

	public void setIgorlea(User igorlea) {
		this.igorlea = igorlea;
	}

	public User getHartzailea() {
		return hartzailea;
	}

	public void setHartzailea(User hartzailea) {
		this.hartzailea = hartzailea;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIgorleID() {
		return igorleID;
	}

	public void setIgorleID(String igorleID) {
		this.igorleID = igorleID;
	}

	public String getHartzaileID() {
		return hartzaileID;
	}

	public void setHartzaileID(String hartzaileID) {
		this.hartzaileID = hartzaileID;
	}
	
	
}
