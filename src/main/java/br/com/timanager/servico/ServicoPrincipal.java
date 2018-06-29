package br.com.timanager.servico;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.DAO.NotificacoesDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.modelo.Notificacao;
import br.com.timanager.modelo.Usuario;

@Startup
@Singleton
public class ServicoPrincipal implements Serializable {

	private static final long serialVersionUID = 5191060712254628679L;

	@Inject
	private GarantiasDAO garantiasDAO;

	@Inject
	private NotificacoesDAO notificacoesDAO;

	@Inject
	private UsuariosDAO usuariosDAO;

	private Integer quantidadeNotificacoes;

	private List<Garantia> garantiasVencidas = new ArrayList<Garantia>();

	public List<Garantia> checarGarantiasVencidas() {
		this.garantiasVencidas = garantiasDAO.garantiasVencidas();
		return this.garantiasVencidas;
	}

	public void garantiasVencidas() {
		this.checarGarantiasVencidas();
	}

	public List<Garantia> getGarantiasVencidas() {
		return garantiasVencidas;
	}

	public void setGarantiasVencidas(List<Garantia> garantiasVencidas) {
		this.garantiasVencidas = garantiasVencidas;
	}

	public List<Notificacao> getNotificacoesUsuario(Usuario usuario) {
		return notificacoesDAO.buscarNotificacoesPor(usuario);
	}

	public Integer getQuantidadeNotificaoesUsuario(Usuario usuario) {
		return notificacoesDAO.buscarNotificacoesPor(usuario).size() > 0
				? notificacoesDAO.buscarNotificacoesPor(usuario).size() : 0;
	}

	public Integer getQuantidadeNotificacoes() {
		return quantidadeNotificacoes;
	}

	public void setQuantidadeNotificacoes(Integer quantidadeNotificacoes) {
		this.quantidadeNotificacoes = quantidadeNotificacoes;
	}

	public StreamedContent getFotoUsuarioLogado(Usuario usuario) {
		final byte[] array = usuario.getFoto() != null ? usuario.getFoto()
				: this.usuariosDAO.buscarFotoPor(usuario.getId());
		return this.instanciarStreamedContent(array);
	}

	private StreamedContent instanciarStreamedContent(final byte[] array) {
		return array != null ? new DefaultStreamedContent(new ByteArrayInputStream(array))
				: new DefaultStreamedContent();
	}

}
