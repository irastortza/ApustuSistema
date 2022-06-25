package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Apustua implements Serializable {
	@XmlID @Id @GeneratedValue 	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer apustuZenbakia;
	private Date mugaData;
	private double apostatutakoDirua;
	private double irabaziak;
	private int kuotaID;
	private String erabID;
	@XmlIDREF 
	private ApustuAnitza apustuAnitza;
	private Integer apustuAnitzaID;
	@XmlIDREF 
	private Erabiltzailea erabiltzailea;
	@XmlIDREF 
	private Kuota kuota;
	
	public Apustua () {
		super();
	}
	
	public Apustua (Date mugaData, double apostatutakoDirua, double irabaziak, Erabiltzailea erabiltzailea, Kuota kuota) {
		this.mugaData=mugaData;
		this.apostatutakoDirua=apostatutakoDirua;
		this.irabaziak=irabaziak;
		this.erabiltzailea=erabiltzailea;
		this.kuota=kuota;
		this.kuotaID=kuota.getKuotaNumber();
		this.erabID=erabiltzailea.getEizena();
		this.apustuAnitzaID=-1;
	}
	
	public Apustua (Date mugaData, double apostatutakoDirua, Erabiltzailea erabiltzailea, Kuota kuota) {
		this.mugaData=mugaData;
		this.apostatutakoDirua=apostatutakoDirua;
		this.erabiltzailea=erabiltzailea;
		this.kuota=kuota;
		this.irabaziak = apostatutakoDirua*kuota.getKuota();
		this.kuotaID=kuota.getKuotaNumber();
		this.erabID=erabiltzailea.getEizena();
		this.apustuAnitzaID=-1;
	}

	public int getKuotaID() {
		return kuotaID;
	}

	public void setKuotaID(int kuotaID) {
		this.kuotaID = kuotaID;
	}

	public String getErabID() {
		return erabID;
	}

	public void setErabID(String erabID) {
		this.erabID = erabID;
	}

	public int getApustuZenbakia() {
		return apustuZenbakia;
	}

	public void setApustuZenbakia(int apustuZenbakia) {
		this.apustuZenbakia = apustuZenbakia;
	}

	public Date getMugaData() {
		return mugaData;
	}

	public void setMugaData(Date mugaData) {
		this.mugaData = mugaData;
	}

	public double getApostatutakoDirua() {
		return apostatutakoDirua;
	}

	public void setApostatutakoDirua(double apostatutakoDirua) {
		this.apostatutakoDirua = apostatutakoDirua;
	}

	public double getIrabaziak() {
		return irabaziak;
	}

	public void setIrabaziak(double irabaziak) {
		this.irabaziak = irabaziak;
	}

	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}

	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
		this.erabID=erabiltzailea.getEizena();
	}

	public Kuota getKuota() {
		return kuota;
	}

	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
		this.kuotaID=kuota.getKuotaNumber();
	}

	public ApustuAnitza getApustuAnitza() {
		return apustuAnitza;
	}

	public void setApustuAnitza(ApustuAnitza apustuAnitza) {
		this.apustuAnitza = apustuAnitza;
		this.apustuAnitzaID=apustuAnitza.getApustuAnitzNumber();
	}
	
	public int getApustuAnitzZenbakia () {
		return apustuAnitzaID;
	}
	
	public void setApustuAnitzaID (int id) {
		this.apustuAnitzaID=id;
	}

	public void setApustuZenbakia(Integer apustuZenbakia) {
		this.apustuZenbakia = apustuZenbakia;
	}
	
	
	
}
