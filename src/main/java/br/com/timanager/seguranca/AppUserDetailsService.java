package br.com.timanager.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.cdi.CDIServiceLocator;
import br.com.timanager.modelo.Autorizacao;
import br.com.timanager.modelo.Grupo;
import br.com.timanager.modelo.Usuario;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
		UsuariosDAO usuariosDAO = CDIServiceLocator.getBean(UsuariosDAO.class);
		Usuario usuario = usuariosDAO.porNome(nome);

		UsuarioSistema user = null;

		if (usuario != null) {
			user = new UsuarioSistema(usuario, getAutorizacoesGrupo(usuario));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}

		return user;
	}

//	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
//		List<SimpleGrantedAuthority> autorizaoces = new ArrayList<>();
//
//		for (Grupo grupo : usuario.getGrupos()) {
//			autorizaoces.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
//		}
//
//		return autorizaoces;
//	}

	private Collection<? extends GrantedAuthority> getAutorizacoesGrupo(Usuario usuario) {
		List<SimpleGrantedAuthority> autorizaoces = new ArrayList<>();

		for (Grupo grupo : usuario.getGrupos()) {
			for (Autorizacao aut : grupo.getAutorizacoes()) {
				autorizaoces.add(new SimpleGrantedAuthority("ROLE_" + aut.getNome()));
			}
		}

		return autorizaoces;
	}

}