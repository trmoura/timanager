package br.com.timanager.dominio;

public enum DominioTemplateEmail {

	TESTE("teste.html"),
	PADRAO("padrao.html"),
	ABERTURA_CHAMADO("abertura-chamado.html"),
	ENCERRAMENTO_CHAMADO("encerramento-chamado.html");

	private String arquivo;

	private DominioTemplateEmail(String arquivo) {

		this.arquivo = arquivo;
	}

	public String getArquivo() {

		return this.arquivo;
	}

}
