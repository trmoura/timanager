package br.com.timanager.dominio;



public enum DominioTipoArquivo {

	PDF("application/pdf", ".pdf"),
	RTF("text/rtf", ".rtf"),
	XLS("application/xls", ".xls");

	private String contentType;

	private String extensao;

	private DominioTipoArquivo(final String contentType, final String extensao) {

		this.contentType = contentType;
		this.extensao = extensao;
	}

	public String getContentType() {

		return this.contentType;
	}

	public String getExtensao() {

		return this.extensao;
	}

}