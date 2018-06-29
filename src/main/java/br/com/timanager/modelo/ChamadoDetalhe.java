package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "chamado_detalhe")
public class ChamadoDetalhe implements BaseEntity, Serializable {

	private static final long serialVersionUID = -8910396724026277808L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataInteracao")
	private Date dataInteracao;

	@NotEmpty
	@Column(name = "descricao_detalhe", nullable = false, length = 300)
	private String descricaoDetalhe;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chamado_id", nullable = false)
	private Chamado chamado;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInteracao() {
		return dataInteracao;
	}

	public void setDataInteracao(Date dataInteracao) {
		this.dataInteracao = dataInteracao;
	}

	public String getDescricaoDetalhe() {
		return descricaoDetalhe;
	}

	public void setDescricaoDetalhe(String descricaoDetalhe) {
		this.descricaoDetalhe = descricaoDetalhe;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

}
