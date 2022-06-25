package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kuota implements Serializable{
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer kuotaNumber;
	private String deskribapena;
	private double kuota;
	private boolean emaitza;
	private boolean bukatuta;
	private int questionID;
	@XmlIDREF
	private Question galdera;
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Apustua> apustuak = new Vector<Apustua> ();
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<ApustuAnitza> apustuAnitzak = new Vector<ApustuAnitza> ();
	
	public Kuota () {
		super();
	}
	
	public Kuota (String deskribapena, double kuota, Question galdera) {
		this.deskribapena=deskribapena;
		this.kuota=kuota;
		this.galdera=galdera;
		this.questionID=galdera.getQuestionNumber();
		this.emaitza=false;
	}
	
	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public Integer getKuotaNumber() {
		return kuotaNumber;
	}

	public void setKuotaNumber(Integer kuotaNumber) {
		this.kuotaNumber = kuotaNumber;
	}

	public String getDeskribapena() {
		return deskribapena;
	}

	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}

	public double getKuota() {
		return kuota;
	}

	public void setKuota(double kuota) {
		this.kuota = kuota;
	}

	public boolean isEmaitza() {
		return emaitza;
	}

	public void setEmaitza(boolean emaitza) {
		this.emaitza = emaitza;
	}

	public Question getGaldera() {
		return galdera;
	}

	public void setGaldera(Question galdera) {
		this.galdera = galdera;
		this.questionID=galdera.getQuestionNumber();
	}
	
	public boolean equals (Object arg0) {
		if (arg0 == this) {
			return true;
		}
		if (!(arg0 instanceof Kuota)) {
			return false;
		}
		Kuota k = (Kuota) arg0;
		return (k.deskribapena.equals(this.deskribapena));
	}

	public Vector<Apustua> getApustuak() {
		return apustuak;
	}

	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}

	public Vector<ApustuAnitza> getApustuAnitzak() {
		return apustuAnitzak;
	}

	public void setApustuAnitzak(Vector<ApustuAnitza> apustuAnitzak) {
		this.apustuAnitzak = apustuAnitzak;
	}

	/**
	 * @return the bukatuta
	 */
	public boolean isBukatuta() {
		return bukatuta;
	}

	/**
	 * @param bukatuta the bukatuta to set
	 */
	public void setBukatuta(boolean bukatuta) {
		this.bukatuta = bukatuta;
	}
	
	

}
