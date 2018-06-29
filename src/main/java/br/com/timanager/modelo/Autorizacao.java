package br.com.timanager.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "autorizacao")
public class Autorizacao implements BaseEntity, GrantedAuthority {

	private static final long serialVersionUID = 3788015363830723215L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "NOME", unique = true, length = 40, nullable = false)
	private String nome;

	@Column(name = "ALIAS", unique = true, length = 60, nullable = false)
	private String aliasRole;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.aliasRole;
	}

	public String getAliasRole() {
		return aliasRole;
	}

	public void setAliasRole(String aliasRole) {
		this.aliasRole = aliasRole;
	}

}
