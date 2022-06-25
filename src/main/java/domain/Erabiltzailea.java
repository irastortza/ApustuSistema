package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlIDREF;

@Entity @XmlAccessorType(XmlAccessType.FIELD)
public class Erabiltzailea extends User implements Serializable{
	private int adina;
	private double dirua;
	private Vector<Integer> mugimenduakID = new Vector<Integer>();
	private Vector<Integer> apustuakID = new Vector<Integer> ();
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Mugimendua> mugimenduak = new Vector<Mugimendua>();
	@XmlIDREF @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Apustua> apustuak = new Vector<Apustua> ();
	
	public Erabiltzailea () {
		super();
	}
	
	public Erabiltzailea(String izena_abizenak, String eizena, int adina, String pasahitza) {
		super(izena_abizenak,eizena,pasahitza);
		this.adina = adina;
		this.dirua = 0.0;
		mugimenduakID = new Vector<Integer> ();
		apustuakID = new Vector<Integer> ();
	}
	
	public String getIzena_abizenak() {
		return super.getIzena_abizenak();
	}

	public void setIzena_abizenak(String izena_abizenak) {
		super.setIzena_abizenak(izena_abizenak);
	}

	public String getEizena() {
		return super.getEizena();
	}

	public void setEizena(String eizena) {
		super.setEizena(eizena);
	}

	public String getPasahitza() {
		return super.getPasahitza();
	}

	public void setPasahitza(String pasahitza) {
		super.setPasahitza(pasahitza);
	}

	public Vector<User> getKontaktuak() {
		return super.getKontaktuak();
	}

	public void setKontaktuak(Vector<User> kontaktuak) {
		super.setKontaktuak(kontaktuak);
	}
	

	public void diruaKendu(double pdirua) {
		this.dirua -= pdirua;
	}
	
	public void diruaGehitu(double pdirua) {
		this.dirua += pdirua;
	}

	/**
	 * @return the adina
	 */
	public int getAdina() {
		return adina;
	}

	/**
	 * @param adina the adina to set
	 */
	public void setAdina(int adina) {
		this.adina = adina;
	}


	/**
	 * @return the dirua
	 */
	public double getDirua() {
		return dirua;
	}

	/**
	 * @param dirua the dirua to set
	 */
	public void setDirua(int dirua) {
		this.dirua = dirua;
	}
	
	public Vector<Integer> getMugimenduakID() {
		return mugimenduakID;
	}

	public void setMugimenduakID(Vector<Integer> mugimenduakID) {
		this.mugimenduakID = mugimenduakID;
	}

	public Vector<Integer> getApustuakID() {
		return apustuakID;
	}

	public void setApustuakID(Vector<Integer> apustuakID) {
		this.apustuakID = apustuakID;
	}

	public void gehituMugimendua (Mugimendua m) {
		this.mugimenduakID.add(m.getMugiNumber());
		this.mugimenduak.add(m);
	}
	
	public void gehituApustua (Apustua ap) {
		this.apustuakID.add(ap.getApustuZenbakia());
		this.apustuak.add(ap);
	}

	/**
	 * @return the mugimenduak
	 */
	public Vector<Mugimendua> getMugimenduak() {
		return mugimenduak;
	}

	/**
	 * @param mugimenduak the mugimenduak to set
	 */
	public void setMugimenduak(Vector<Mugimendua> mugimenduak) {
		this.mugimenduak = mugimenduak;
	}

	public Vector<Apustua> getApustuak() {
		return apustuak;
	}

	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}


	public void setDirua(double dirua) {
		this.dirua = dirua;
	}
	
	
	
}
