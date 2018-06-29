package br.com.timanager.interfaces;

import java.io.Serializable;

public interface Dominio extends Serializable {

	/**
	 * Retorna o codigo do respectivo dominio, em geral o mesmo que vai para o
	 * BD.
	 * 
	 * @return o codigo do domínio.
	 */
	public Integer getCodigo();

	/**
	 * Retorna a descrição em string do dominio. Em geral o toString de uma
	 * enumeration.
	 * 
	 * @return a descrição do domínio.
	 */
	public String getDescricao();

}