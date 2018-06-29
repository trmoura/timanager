package br.com.timanager.interfaces;

import br.com.timanager.dominio.DominioTipoArquivo;

public interface IArquivo {

	public String getNome();

	public byte[] getConteudo();

	public DominioTipoArquivo getTipoDeArquivo();
}