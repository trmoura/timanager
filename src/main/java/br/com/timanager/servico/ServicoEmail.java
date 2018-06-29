package br.com.timanager.servico;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.timanager.DAO.ParametrosDAO;
import br.com.timanager.cdi.CDIServiceLocator;
import br.com.timanager.dominio.DominioTemplateEmail;
import br.com.timanager.interfaces.IArquivo;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.seguranca.Seguranca;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Startup
@Singleton
public class ServicoEmail {

	private ParametrosDAO parametrosDAO;

	@Inject
	Seguranca seguranca;

	private static final String TEMPLATES_DIR = "/emailTemplates";

	public ServicoEmail() {
		this.parametrosDAO = CDIServiceLocator.getBean(ParametrosDAO.class);
	}

	private String carregarTemplate(DominioTemplateEmail template, Map<String, Object> modelo) throws Exception {

		final TemplateLoader templateLoader = new ClassTemplateLoader(ServicoEmail.class, ServicoEmail.TEMPLATES_DIR);
		final Configuration cfg = new Configuration();
		cfg.setTemplateLoader(templateLoader);
		cfg.setDefaultEncoding("UTF-8");
		final Template arquivo = cfg.getTemplate(template.getArquivo());
		final StringWriter buffer = new StringWriter();
		arquivo.process(modelo, buffer);
		return buffer.toString();
	}

	/**
	 * Método que envia um email com o resultado de um template.
	 *
	 * @param emailDe
	 *            : Email do remetente.
	 * @param nomeDe
	 *            : Nome do remetente.
	 * @param emailPara
	 *            : Email do destinatário.
	 * @param nomePara
	 *            : Nome do destinatário.
	 * @param assunto
	 *            : Assunto do email.
	 * @param template
	 *            : Template do email.
	 * @param parametros
	 *            : Mapa de objetos para o conteúdo do email.
	 * @throws Exception
	 */
	@Asynchronous
	public void enviarEmail(String emailDe, String nomeDe, String emailPara, String nomePara, String assunto,
			DominioTemplateEmail template, Map<String, Object> parametros, IArquivo... arquivo) throws Exception {

		final HtmlEmail email = this.getHtmlEmail();

		if (arquivo != null) {
			for (IArquivo anexo : arquivo) {
				if (anexo != null) {
					email.attach(this.getAnexo(anexo), anexo.getNome(), anexo.getNome());
				}
			}
		}

		email.setFrom(emailDe, nomeDe);
		email.addTo(emailPara, nomePara);
		email.setSubject(assunto);
		email.setHtmlMsg(this.carregarTemplate(template, parametros));
		email.send();
	}

	public void enviarMalaDireta(String emailPara, String assunto, byte[] arquivo, DominioTemplateEmail template,
			Map<String, Object> parametros) throws NegocioException {

		this.enviarMalaDireta(this.getEnderecos(emailPara), assunto, arquivo, template, parametros);
	}

	public void enviarMalaDireta(Collection<InternetAddress> enderecos, String assunto, byte[] arquivo,
			DominioTemplateEmail template, Map<String, Object> parametros) throws NegocioException {

		try {
			final HtmlEmail email = this.getHtmlEmail();
			email.setBcc(enderecos);

			email.setFrom(parametrosDAO.getUsuarioRemetente().getValor(),
					seguranca.getUsuarioLogado().getUsuario().getNome());
			email.setSubject(assunto);

			if (arquivo != null) {

				parametros.put("imagem",
						" <img src='cid:" + email.embed(this.getAnexo(arquivo, "image/jpeg"), "imagem.jpg") + "'> ");
			}

			email.setHtmlMsg(this.carregarTemplate(template, parametros));

			email.send();
		} catch (EmailException e) {
			throw new NegocioException(e.getMessage());
		} catch (IOException e) {
			throw new NegocioException(e.getMessage());
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private Collection<InternetAddress> getEnderecos(String emailPara) {

		final List<String> emails = StringUtils.contains(emailPara, ";")
				? Arrays.asList(StringUtils.split(emailPara.trim(), ";"))
				: Arrays.asList(StringUtils.split(emailPara.trim(), ","));

		return CollectionUtils.collect(emails, new Transformer<String, InternetAddress>() {

			@Override
			public InternetAddress transform(String email) {

				try {
					return new InternetAddress(email);
				} catch (AddressException e) {
					return null;
				}
			}
		});
	}

	@Asynchronous
	public void enviarEmail(String emailPara, String nomePara, String assunto, String mensagem, IArquivo... arquivo)
			throws Exception {

		final HtmlEmail email = this.getHtmlEmail();

		if (arquivo != null) {
			for (IArquivo anexo : arquivo) {
				if (anexo != null) {
					email.attach(this.getAnexo(anexo), anexo.getNome(), anexo.getNome());
				}
			}
		}

		email.setFrom(parametrosDAO.getUsuarioRemetente().getValor(),
				seguranca.getUsuarioLogado().getUsuario().getNome());
		email.addTo(emailPara, nomePara);
		email.setSubject(assunto);
		email.setMsg(mensagem);
		email.send();
	}

	private HtmlEmail getHtmlEmail() {

		final HtmlEmail email = new HtmlEmail();

		final String hostname = parametrosDAO.getServidorSMTP().getValor();

		email.setCharset("UTF-8");
		email.setDebug(false);
		email.setHostName(hostname);
		email.setAuthenticator(new DefaultAuthenticator(parametrosDAO.getUsuarioRemetente().getValor(),
				parametrosDAO.getSenhaEMAIL().getValor()));
		email.setSmtpPort(Integer.parseInt(parametrosDAO.getPortaSMTP().getValor()));

		email.setStartTLSEnabled(true);
		email.setSSLOnConnect(true);

		return email;
	}

	public void enviarEmail(String emailPara, String nomePara, String assunto, DominioTemplateEmail template,
			Map<String, Object> parametros, IArquivo... arquivo) throws Exception {

		this.enviarEmail(parametrosDAO.getUsuarioRemetente().getValor(),
				seguranca.getUsuarioLogado().getUsuario().getNome(), emailPara, nomePara, assunto, template, parametros,
				arquivo);
	}

	public void enviarEmail(String emailPara, String nomePara, String assunto, DominioTemplateEmail template,
			Map<String, Object> parametros) throws Exception {

		this.enviarEmail(parametrosDAO.getUsuarioRemetente().getValor(),
				seguranca.getUsuarioLogado().getUsuario().getCargo() + " - "
						+ seguranca.getUsuarioLogado().getUsuario().getNome(),
				emailPara, nomePara, assunto, template, parametros, null);
	}

	public void enviarEmail(String emailPara, String nomePara, String assunto, DominioTemplateEmail template)
			throws Exception {

		this.enviarEmail(parametrosDAO.getUsuarioRemetente().getValor(), "ABERTURA DE CHAMADO - CASA DO FERRO",
				emailPara, nomePara, assunto, template, null);
	}

	public DataSource getAnexo(final IArquivo arquivo) throws IOException {

		return new ByteArrayDataSource(new ByteArrayInputStream(arquivo.getConteudo()),
				arquivo.getTipoDeArquivo().getContentType());
	}

	public DataSource getAnexo(final byte[] arquivo, final String contentType) throws IOException {

		return new ByteArrayDataSource(new ByteArrayInputStream(arquivo), contentType);
	}

}
