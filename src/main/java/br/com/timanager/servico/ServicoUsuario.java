package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Usuario;

public class ServicoUsuario implements Serializable {

	private static final long serialVersionUID = -2048814579844974110L;

	@Inject
	private UsuariosDAO usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioExistente = usuarios.porId(usuario.getId());

		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um usuario com o id informado.");
		}

		return usuarios.guardar(usuario);
	}

}