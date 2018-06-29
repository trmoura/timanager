package br.com.timanager.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);

		http.csrf().disable().headers().frameOptions().sameOrigin().and()

				.authorizeRequests().antMatchers("/login.xhtml", "/Erro.xhtml")

				.permitAll().antMatchers("/Home.xhtml", "/AcessoNegado.xhtml", "/dialogos/**").authenticated()
				.antMatchers("/cadastro/peca/novapeca.xhtml").hasRole("CADASTRO_ITEM")
				.antMatchers("/cadastro/marca/novamarca.xhtml").hasRole("CADASTRO_MARCA")
				.antMatchers("/cadastro/setor/novosetor.xhtml").hasRole("CADASTRO_SETOR")
				.antMatchers("/cadastro/item/tipodeitem.xhtml").hasRole("CADASTRO_TIPO_ITEM")
				.antMatchers("/cadastro/grupos/novogrupo.xhtml").hasRole("CADASTRO_GRUPO")
				.antMatchers("/cadastro/usuarios/novoUsuario.xhtml").hasRole("CADASTRO_USUARIO")
				.antMatchers("/controle/garantia/montargarantia.xhtml").hasRole("CONTROLE_GARANTIA")
				.antMatchers("/controle/garantia/garantiadetalhe.xhtml").hasRole("CONTROLE_GARANTIA")
				.antMatchers("/controle/garantia/baixagarantia.xhtml").hasRole("CONTROLE_GARANTIA_BX")
				.antMatchers("/controle/garantia/pesquisagarantia.xhtml").hasRole("CONTROLE_GARANTIA_PESQ")
				.antMatchers("/controle/notas/notadeentrada.xhtml").hasRole("CONTROLE_NOTAS")
				.antMatchers("/controle/notas/itensnotaentrada.xhtml").hasRole("CONTROLE_NOTAS")
				.antMatchers("/controle/notas/pesquisanotas.xhtml").hasRole("CONTROLE_NOTAS_PES")
				.antMatchers("/controle/transferencia/transferenciadeitens.xhtml").hasRole("CONTROLE_TRANSFERENCIA")
				.antMatchers("/controle/transferencia/detalhestransferencia.xhtml").hasRole("CONTROLE_TRANSFERENCIA")
				.antMatchers("/suporte/novochamado.xhtml").hasRole("SUPORTE_CHAMADO")
				.antMatchers("/suporte/detalheschamado.xhtml").hasRole("SUPORTE_CHAMADO")
				.antMatchers("/suporte/consultarchamados.xhtml").hasRole("SUPORTE_CHAMADO")
				.antMatchers("/suporte/manutecaochamado.xhtml").hasRole("SUPORTE_CHAMADO_MAN")
				.antMatchers("/relatoriosPages/RelacaoEmpresaSetorPeca.xhtml").hasRole("REL_EMPRESA_SETEOR")
				.antMatchers("/relatoriosPages/RelatorioGarantiaEmpresa.xhtml").hasRole("REL_EMPRESA_GARANTIA")
				.antMatchers("/relatoriosPages/RelatorioMovimentacoes.xhtml").hasRole("REL_EMPRESA_MOVIMENTACAO")
				.antMatchers("/relatoriosPages/RelatorioSinteticoItens.xhtml").hasRole("REL_SINTETICO_ITENS")
				.antMatchers("/consultas/consultaMapaEquipamentos.xhtml").hasRole("CONSULTA_MAPA_EQUIPAMENTOS")
				
				
				.and()

				.formLogin().loginPage("/login.xhtml").failureUrl("/login.xhtml?invalid=true").and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()

				.exceptionHandling().accessDeniedPage("/AcessoNegado.xhtml").authenticationEntryPoint(jsfLoginEntry)
				.accessDeniedHandler(jsfDeniedEntry);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/javax.faces.resource/**", "/resources/**", "/fonts/**",
				"/resources/fonts/**","/images/**");
	}

}