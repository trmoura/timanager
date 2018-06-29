package br.com.timanager.xmlNF;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "prod")
public class Prod {

	@XmlElement(name = "cProd")
	private String cProd;

	@XmlElement(name = "xProd")
	private String xProd;

	@XmlElement(name = "vProd", type = BigDecimal.class)
	private BigDecimal vProd;

	@XmlElement(name = "qCom", type = BigDecimal.class)
	private BigDecimal qCom;

	public String getcProd() {
		return cProd;
	}

	public BigDecimal getqCom() {
		return qCom;
	}

	public void setqCom(BigDecimal qCom) {
		this.qCom = qCom;
	}

	public void setcProd(String cProd) {
		this.cProd = cProd;
	}

	public String getxProd() {
		return xProd;
	}

	public void setxProd(String xProd) {
		this.xProd = xProd;
	}

	public BigDecimal getvProd() {
		return vProd;
	}

	public void setvProd(BigDecimal vProd) {
		this.vProd = vProd;
	}

}
