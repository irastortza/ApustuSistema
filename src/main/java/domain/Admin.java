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
public class Admin extends User implements Serializable {
	
	
	public Admin () {
		super();
	}
	
	public Admin(String izena_abizenak, String eizena, String pasahitza) {
		super(izena_abizenak,eizena,pasahitza);
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



	
	
}
