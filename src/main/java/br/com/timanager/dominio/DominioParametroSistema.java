package br.com.timanager.dominio;

import br.com.timanager.interfaces.Dominio;

public enum DominioParametroSistema implements Dominio {

	URL_SISTEMA("Url do Sistema"),
	URL_SISTEMA_LOCAL("Url do Sistema Localhost"),
	URL_BANCO_DADOS_PRODUCAO("Url do Banco de Dados Producao"),
	URL_BANCO_DADOS_LOCAL("Url do Banco de Dados Localhost"),
	SENHA_BANCO_PRODUCAO("Senha Banco Produção"),
	SENHA_BANCO_LOCAL("Senha Banco Local"),
	USUARIO_BANCO_PRODUCAO("Usuário banco produção"),
	USUARIO_BANCO_LOCAL("Usuário banco local"),
	LOCALHOST("Sistema em modo Localhost"),
	NOME_DO_BANCO_DE_DADOS("Nome do Banco de Dados"),
	USUARIO_EMAIL("Usuário E-mail"),
	SENHA_EMAIL("Senha E-mail"),
	SERVIDOR_SMTP("Servidor SMTP"),
	PORTA_SMTP("Porta do servidor e-mail");

	private String descricao;

	private DominioParametroSistema(String descricao) {

		this.descricao = descricao;
	}

	@Override
	public Integer getCodigo() {

		return this.ordinal();
	}

	@Override
	public String getDescricao() {

		return this.descricao;
	}

	@Override
	public String toString() {

		return this.descricao;
	}

}