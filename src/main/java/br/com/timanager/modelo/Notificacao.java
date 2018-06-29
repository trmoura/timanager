package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "notificacao")
public class Notificacao implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1645961787829701545L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "titulo", length = 40)
	private String titulo;

	@NotNull
	@Column(name = "mensagem", length = 200)
	private String mensagem;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataNotificacao")
	private Date dataNotificacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "lida")
	private DominioSimNao lida;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DominioSimNao getLida() {
		return lida;
	}

	public void setLida(DominioSimNao lida) {
		this.lida = lida;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(Date dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}

}
