package br.com.timanager.relatorio;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.interfaces.IArquivo;

@ApplicationScoped
public class RemetenteWeb {

	/**
	 * Download de arquivo via FILE DOWNLOAD do primefaces 4.0 ex: <h:form>
	 * <p:commandButton value="Download" ajax="false" onclick="PrimeFaces.monitorDownload(start, stop);" icon="ui-icon-arrowthick-1-s">
	 * <p:fileDownload value="#{fileDownloadView.file}" />
	 * </p:commandButton>
	 * </h:form>
	 *
	 * @param arquivo
	 * @return StreamedContent
	 */
	public static StreamedContent download(final IArquivo arquivo) {

		return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getConteudo()), arquivo.getTipoDeArquivo().getContentType(),
						arquivo.getNome());
	}

	public static void enviarParaDownload(final IArquivo arquivo) {

		final FacesContext context = FacesContext.getCurrentInstance();
		final HttpServletResponse httpResponse = (HttpServletResponse) context.getExternalContext().getResponse();
		httpResponse.setCharacterEncoding("ISO-8859-1");
		httpResponse.setContentType(arquivo.getTipoDeArquivo().getContentType());
		httpResponse.addHeader("Content-Disposition", "attachment;filename=" + arquivo.getNome());
		try {
			httpResponse.getOutputStream().write(arquivo.getConteudo());
			httpResponse.getOutputStream().flush();
			context.responseComplete();
			httpResponse.getOutputStream().close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
