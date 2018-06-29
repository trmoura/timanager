package br.com.timanager.controlador;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.timanager.jsf.FacesUtil;

@Named
@SessionScoped
public class ControladorLoginBean implements Serializable {

	private static final long serialVersionUID = 2958002824543678406L;

	@Inject
	FacesContext facesContext;
	
	@Inject
	HttpServletRequest request;
	
	@Inject
	HttpServletResponse response;

	private String email;

	public static final String REDIREC_HOME = "faces/Home?faces-redirect=true";

	public void preRender() {
		if ("true".equals(request.getParameter("invalid"))) {
			FacesUtil.addErrorMessage("Usuário ou senha inválido!");
		}
	}

	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.xhtml");
		dispatcher.forward(request, response);
		facesContext.responseComplete();
	}

	public boolean isPossuiUsuarioLogado() {

		return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				.equals("anonymousUser");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String redirectHome() {
		return REDIREC_HOME;
	}

}