package br.com.timanager.xmlNF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "nfeProc", namespace = "http://www.portalfiscal.inf.br/nfe")
@XmlType(propOrder = { "nfeProc", "NFe", "protNFe" })
public class nfeProc {

	@XmlElement(name = "nfeProc", namespace = "http://www.portalfiscal.inf.br/nfe")
	private String nfeProc;

	@XmlElement
	private NFE NFe;

	@XmlAttribute(name = "versao")
	private String versao;

	@XmlElement(name = "protNFe")
	private ProtNFe protNFe;

	public NFE getNFe() {
		return NFe;
	}

	public void setNFe(NFE nFe) {
		NFe = nFe;
	}

	public String getNfeProc() {
		return nfeProc;
	}

	public void setNfeProc(String nfeProc) {
		this.nfeProc = nfeProc;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public ProtNFe getProtNFe() {
		return protNFe;
	}

	public void setProtNFe(ProtNFe protNFe) {
		this.protNFe = protNFe;
	}

}