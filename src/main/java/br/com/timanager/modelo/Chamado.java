package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.timanager.dominio.DominioSituacaoChamado;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "chamado")
public class Chamado implements BaseEntity, Serializable {

	private static final long serialVersionUID = 7242916201601309324L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataAbertura")
	private Date dataAbertura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataEncerramento")
	private Date dataEncerramento;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Empresa empresa;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Setor setor;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario usuario;

	@NotNull
	@Column(name = "descricao_chamado", length = 500)
	private String descricaoChamado;

	@Column(name = "parecer", length = 500)
	private String parecer;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_chamado")
	private DominioSituacaoChamado situacaoChamado;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Usuario tecnico;
	
	@OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ChamadoDetalhe> chamadoDetalhes = new ArrayList<ChamadoDetalhe>();

	@Override
	public Long getId() {
		return this.id;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoChamado() {
		return descricaoChamado;
	}

	public void setDescricaoChamado(String descricaoChamado) {
		this.descricaoChamado = descricaoChamado;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public DominioSituacaoChamado getSituacaoChamado() {
		return situacaoChamado;
	}

	public void setSituacaoChamado(DominioSituacaoChamado situacaoChamado) {
		this.situacaoChamado = situacaoChamado;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Usuario getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	public List<ChamadoDetalhe> getChamadoDetalhes() {
		return chamadoDetalhes;
	}

	public void setChamadoDetalhes(List<ChamadoDetalhe> chamadoDetalhes) {
		this.chamadoDetalhes = chamadoDetalhes;
	}

}
