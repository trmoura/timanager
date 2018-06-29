package br.com.timanager.xmlNF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NFe", namespace = "http://www.portalfiscal.inf.br/nfe")
public class NFE {

	@XmlElement(name = "infNFe")
	private InfNFe infNFe;

	public InfNFe getInfNFe() {
		return infNFe;
	}

	public void setInfNFe(InfNFe infNFe) {
		this.infNFe = infNFe;
	}

	
}
