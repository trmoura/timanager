package br.com.timanager.xmlNF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "protNFe")
public class ProtNFe {

	@XmlAttribute(name = "versao")
	private String versao;

	@XmlElement(name = "infProt")
	private InfProt infProt;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public InfProt getInfProt() {
		return infProt;
	}

	public void setInfProt(InfProt infProt) {
		this.infProt = infProt;
	}

}
