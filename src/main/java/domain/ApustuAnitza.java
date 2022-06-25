package domain;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class ApustuAnitza implements Serializable {
	@Id 
	@GeneratedValue @XmlID 	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer apustuAnitzNumber;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Apustua> apustuak;
	private Date lehenMugaData;
	private double sartutakoDirua;
	private double irabaziak;
	private boolean aktibo;
	private int faltan;
	private int apustuKopurua;
	
	public ApustuAnitza () {
		faltan=0;
		aktibo=true;
		irabaziak=0;
		sartutakoDirua=0;
		apustuak = new Vector<Apustua> ();
		this.apustuKopurua=0;
	}
	
	public ApustuAnitza (double sartutakoDirua, double irabaziak) {
		this.sartutakoDirua=sartutakoDirua;
		this.irabaziak=irabaziak;
		faltan=0;
		aktibo=true;
		apustuak = new Vector<Apustua> ();
		this.apustuKopurua=0;
	}
	
	public void gehituApustua (Apustua ap, Date gertaeraHurbilenarenData) {
		this.apustuak.add(ap);
		this.faltan++;
		this.apustuKopurua++;
		this.lehenMugaData=gertaeraHurbilenarenData;
	}
	
	public boolean irabaziDu () {
		return(faltan == 0);
	}
	
	public void apustuBatGalduDu () {
		this.aktibo=false;
	}
	
	public void apustuBatIrabaziDu () {
		if (aktibo) faltan--;
	}

	public Integer getApustuAnitzNumber() {
		return apustuAnitzNumber;
	}

	public void setApustuAnitzNumber(Integer apustuAnitzNumber) {
		this.apustuAnitzNumber = apustuAnitzNumber;
	}

	public Vector<Apustua> getApustuak() {
		return apustuak;
	}

	public double getIrabaziak() {
		return irabaziak;
	}

	public void setIrabaziak(double irabaziak) {
		this.irabaziak = irabaziak;
	}

	public boolean isAktibo() {
		return aktibo;
	}

	public void setAktibo(boolean aktibo) {
		this.aktibo = aktibo;
	}

	public int getFaltan() {
		return faltan;
	}

	public void setFaltan(int faltan) {
		this.faltan = faltan;
	}

	public double getSartutakoDirua() {
		return sartutakoDirua;
	}

	public void setSartutakoDirua(double sartutakoDirua) {
		this.sartutakoDirua = sartutakoDirua;
	}

	public void setKuotak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}

	public Date getLehenMugaData() {
		return lehenMugaData;
	}

	public void setLehenMugaData(Date lehenMugaData) {
		this.lehenMugaData = lehenMugaData;
	}

	public int getApustuKopurua() {
		return apustuKopurua;
	}

	public void setApustuKopurua(int apustuKopurua) {
		this.apustuKopurua = apustuKopurua;
	}

	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	
	
}
