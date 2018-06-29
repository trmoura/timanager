package br.com.timanager.xmlNF;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.exolab.castor.types.DateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ide")
public class Ide {

	@XmlElement(name = "nNF")
	private String nNF;

	@XmlElement(name = "dhEmi")
	private String dhEmi;

	@XmlElement(name = "dEmi")
	private String dEmi;

	public String getnNF() {
		return nNF;
	}

	public void setnNF(String nNF) {
		this.nNF = nNF;
	}

	public String getDhEmi() {
		return dhEmi;
	}

	public void setDhEmi(String dhEmi) {
		this.dhEmi = dhEmi;
	}

	public String getdEmi() {
		return dEmi;
	}

	public void setdEmi(String dEmi) {
		this.dEmi = dEmi;
	}

}
