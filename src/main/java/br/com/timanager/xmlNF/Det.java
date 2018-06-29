package br.com.timanager.xmlNF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "det")
public class Det {

	@XmlAttribute(name = "nItem")
	private int nItem;

	@XmlElement(name = "prod")
	private Prod prod;

	public int getnItem() {
		return nItem;
	}

	public void setnItem(int nItem) {
		this.nItem = nItem;
	}

	public Prod getcProd() {
		return prod;
	}

	public void setcProd(Prod cProd) {
		this.prod = cProd;
	}

}
