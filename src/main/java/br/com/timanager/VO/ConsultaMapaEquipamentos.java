package br.com.timanager.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class ConsultaMapaEquipamentos implements Serializable {

	private static final long serialVersionUID = 7762963513962139320L;

	BigInteger id;

	String descricao;

	BigInteger qtd;

	public ConsultaMapaEquipamentos(BigInteger id, String descricao, BigInteger qtd) {
		this.id = id;
		this.descricao = descricao;
		this.qtd = qtd;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigInteger getQtd() {
		return qtd;
	}

	public void setQtd(BigInteger qtd) {
		this.qtd = qtd;
	}

}
