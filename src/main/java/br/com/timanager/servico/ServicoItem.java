package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.timanager.DAO.ItensDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Item;

public class ServicoItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -363808414242367594L;
	@Inject
	private ItensDAO itens;

	public Item salvar(Item item) {
		Item itemExistente = itens.porId(item.getId());
		if (itemExistente != null && !itemExistente.equals(item)) {
			throw new NegocioException("Já existe um Item com o id informado.");
		}

		if (item.getEmpresa() == null) {
			throw new NegocioException("Selecione um Empresa.");
		}

		return itens.guardar(item);
	}

}