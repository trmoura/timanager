package br.com.timanager.relatorio;

import javax.enterprise.context.ApplicationScoped;

import org.primefaces.model.StreamedContent;

import br.com.timanager.interfaces.IArquivo;

@ApplicationScoped
public class GeradorRelatorioJasper {

	public StreamedContent gerarRelatorio(final ParametroJasper parametroRelatorio) {

		return RemetenteWeb.download(new ArquivoJasper(parametroRelatorio));
	}

	public StreamedContent gerarRelatorio(final IArquivo arquivo) {

		return RemetenteWeb.download(arquivo);
	}

	public void gerarDownload(final ParametroJasper parametroRelatorio) {

		RemetenteWeb.enviarParaDownload(new ArquivoJasper(parametroRelatorio));
	}

}
