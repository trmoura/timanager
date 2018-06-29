package br.com.timanager.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.timanager.dominio.DominioCargo;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "usuario")
public class Usuario implements BaseEntity, UserDetails {

	private static final long serialVersionUID = 7791729926034272558L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 80)
	private String nome;

	@Column(nullable = false, unique = true, length = 63)
	private String email;

	@Column(nullable = false, length = 20)
	private String senha;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_grupo"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Grupo> grupos = new ArrayList<Grupo>();

	@OneToMany(mappedBy = "usuario", targetEntity = Notificacao.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Notificacao> notificacoes = new ArrayList<Notificacao>();

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "cargo")
	private DominioCargo cargo;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@Enumerated(EnumType.STRING)
	@Column(name = "recebeEmailChamado")
	private DominioSimNao recebeEmailChamado;

	@Lob
	@Column(name = "foto", length = 16777215)
	@Basic(fetch = FetchType.LAZY)
	private byte[] foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public DominioCargo getCargo() {
		return cargo;
	}

	public void setCargo(DominioCargo cargo) {
		this.cargo = cargo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return nome;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public DominioSimNao getRecebeEmailChamado() {
		return recebeEmailChamado;
	}

	public void setRecebeEmailChamado(DominioSimNao recebeEmailChamado) {
		this.recebeEmailChamado = recebeEmailChamado;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Autorizacao> autorizacoes = new HashSet<Autorizacao>();
		if (grupos != null) {
			for (Grupo grupo : grupos) {
				autorizacoes.addAll(grupo.getAutorizacoes());
			}
		}
		return autorizacoes;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.nome;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}