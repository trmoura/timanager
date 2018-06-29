package br.com.timanager.relatorio;

import org.primefaces.model.StreamedContent;

import br.com.timanager.interfaces.IArquivo;

public interface IRemetente {

	public StreamedContent download(final IArquivo arquivo);
	
	public void enviarParaDownload(final IArquivo arquivo);

}
