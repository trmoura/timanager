package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioMes implements Dominio {

	JANEIRO(1, "JANEIRO", "01"),
	FEVEREIRO(2, "FEVEVEIRO", "02"),
	MARCO(3, "MARÇO", "03"),
	ABRIL(4, "ABRIL", "04"),
	MAIO(5, "MAIO", "05"),
	JUNHO(6, "JUNHO", "06"),
	JULHO(7, "JULHO", "07"),
	AGOSTO(8, "AGOSTO", "08"),
	SETEMBRO(9, "SETEMBRO", "09"),
	OUTUBRO(10, "OUTUBRO", "10"),
	NOVEMBRO(11, "NOVEMBRO", "11"),
	DEZEMBRO(12, "DEZEMBRO", "12");

	private Integer codigo;

	private String descricao;

	private String numero;

	private DominioMes(Integer codigo, String descricao, String numero) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.numero = numero;

	}

	@Override
	public Integer getCodigo() {

		return this.codigo;
	}

	@Override
	public String getDescricao() {

		return this.descricao;
	}

	public String getNumero() {

		return this.numero;
	}

	@Override
	public String toString() {

		return this.descricao;
	}

	public static Iterable<DominioMes> list() {

		return Arrays.asList(DominioMes.JANEIRO, DominioMes.FEVEREIRO, DominioMes.MARCO, DominioMes.ABRIL, DominioMes.MAIO, DominioMes.JUNHO,
						DominioMes.JULHO, DominioMes.AGOSTO, DominioMes.SETEMBRO, DominioMes.OUTUBRO, DominioMes.NOVEMBRO, DominioMes.DEZEMBRO);
	}

	public static DominioMes mesAtual(int mes) {

		switch (mes) {
			case 0:
				return JANEIRO;
			case 1:
				return FEVEREIRO;
			case 2:
				return MARCO;
			case 3:
				return ABRIL;
			case 4:
				return MAIO;
			case 5:
				return JUNHO;
			case 6:
				return JULHO;
			case 7:
				return AGOSTO;
			case 8:
				return SETEMBRO;
			case 9:
				return OUTUBRO;
			case 10:
				return NOVEMBRO;
			case 11:
				return DEZEMBRO;

			default:
				return null;
		}
	}

}
