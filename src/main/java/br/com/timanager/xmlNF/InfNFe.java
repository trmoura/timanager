package br.com.timanager.xmlNF;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "infNFe")
public class InfNFe {

	@XmlAttribute(name = "versao")
	private String versao;

	@XmlAttribute(name = "Id")
	private String Id;

	@XmlElement(name = "det")
	private List<Det> detalhes;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@XmlElement(name = "ide")
	private Ide ide;

	public Ide getIde() {
		return ide;
	}

	public void setIde(Ide ide) {
		this.ide = ide;
	}

	public List<Det> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<Det> detalhes) {
		this.detalhes = detalhes;
	}

}
