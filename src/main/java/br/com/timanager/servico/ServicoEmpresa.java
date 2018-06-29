package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Empresa;

public class ServicoEmpresa implements Serializable {

	private static final long serialVersionUID = -3673976800249143320L;
	@Inject
	private EmpresasDAO empresas;

	@Transactional
	public Empresa salvar(Empresa empresa) {
		Empresa empresaExistente = empresas.porId(empresa.getId());
		if (empresaExistente != null && !empresaExistente.equals(empresa)) {
			throw new NegocioException("Já existe uma Empresa com o id informado.");
		}

		return empresas.guardar(empresa);
	}

}