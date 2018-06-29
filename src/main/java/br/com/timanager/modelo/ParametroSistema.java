package br.com.timanager.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.com.timanager.dominio.DominioParametroSistema;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "parametro_sistema")
public class ParametroSistema implements BaseEntity, Serializable {

	private static final long serialVersionUID = 236441202295023924L;

	@Id
	@Column(name = "nome_parametro")
	@Enumerated(EnumType.STRING)
	private DominioParametroSistema nomeParametro;

	@NotBlank
	@Column(name = "valor", nullable = false, length = 80)
	private String valor;

	@Enumerated(EnumType.STRING)
	@Column(name = "visivel")
	private DominioSimNao visivel;

	public ParametroSistema() {

		super();
	}

	public DominioParametroSistema getNomeParametro() {

		return this.nomeParametro;
	}

	public void setNomeParametro(DominioParametroSistema nomeParametro) {

		this.nomeParametro = nomeParametro;
	}

	public String getValor() {

		return this.valor;
	}

	public void setValor(String valor) {

		this.valor = valor;
	}

	public DominioSimNao getVisivel() {

		return this.visivel;
	}

	public void setVisivel(DominioSimNao visivel) {

		this.visivel = visivel;
	}

	@Override
	public String toString() {

		return "ParametroSistema [nomeParametro=" + this.nomeParametro + ", valor=" + this.valor + ", visivel="
				+ this.visivel + "]";
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.getId();
	}

}