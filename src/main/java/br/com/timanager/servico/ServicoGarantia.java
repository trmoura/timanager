package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.modelo.GarantiaDetalhe;


public class ServicoGarantia implements Serializable {

	private static final long serialVersionUID = 3418827314269631953L;
	@Inject
	private GarantiasDAO garantias;

	
	public Garantia salvar(Garantia garantia) {
		Garantia garantiaExistente = garantias.porId(garantia.getId());
		if (garantiaExistente != null && !garantiaExistente.equals(garantia)) {
			throw new NegocioException("Já existe uma Garantia com o id informado.");
		}

		return garantias.guardar(garantia);
	}

	
	public GarantiaDetalhe salvar(GarantiaDetalhe garantiaDetalhe) {
		GarantiaDetalhe garantiadetalheExistente = garantias.detalheGarantiaPorId(garantiaDetalhe.getId());
		if (garantiadetalheExistente != null && !garantiadetalheExistente.equals(garantiaDetalhe)) {
			throw new NegocioException("Já existe uma Detalhe da garantia com o id informado.");
		}

		return garantias.guardarGarantiaDetalhe(garantiaDetalhe);
	}
}