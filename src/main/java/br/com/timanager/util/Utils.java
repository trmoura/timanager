package br.com.timanager.util;

import java.io.Reader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.text.MaskFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import br.com.timanager.modelo.ItemTempXML;
import br.com.timanager.xmlNF.nfeProc;

public class Utils {

	private static final char[] ALL_CHARS = new char[62];

	private static final Random RANDOM = new Random();

	public static String getStringQrcode() {

		Random r = new Random();
		int code = r.nextInt(99999) * 2;

		return StringUtils.leftPad("0", 10) + String.valueOf(code);
	}

	public static String formatarCep(String cep) {

		return Utils.formatString(cep, "#####-###");
	}

	public static String formatarMoeda(BigDecimal valor) {

		return new DecimalFormat("#,##0.00").format(valor);
	}

	public static String formatString(String value, String pattern) {

		MaskFormatter mf;
		try {
			mf = new MaskFormatter(pattern);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException ex) {
			return value;
		}
	}

	public static String getValorAleatorio(final int length) {

		final char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = Utils.ALL_CHARS[Utils.RANDOM.nextInt(Utils.ALL_CHARS.length)];
		}
		return new String(result);
	}

	public boolean verificavencimento(Date emissao, Date vencimento) {

		boolean retorno;

		if (emissao.before(vencimento)) {
			retorno = true;
		} else if (emissao.after(vencimento)) {
			retorno = false;
		} else {
			retorno = true;
		}
		return retorno;
	}

	public static void main(String[] args) {

	}

	public static List<ItemTempXML> processaArquivoXMLNOTA(Reader arquivo) {
		try {
			List<ItemTempXML> listaItens = new ArrayList<ItemTempXML>();
			// File file = new File("E:\\nota2.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(nfeProc.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			nfeProc ob = (nfeProc) jaxbUnmarshaller.unmarshal(arquivo);
			System.out.println("Número da Nota :" + ob.getNFe().getInfNFe().getIde().getnNF());
			System.out.println("Data de emissão v2:" + ob.getNFe().getInfNFe().getIde().getdEmi());
			System.out.println("Data de emissão v3:" + ob.getNFe().getInfNFe().getIde().getDhEmi());

			for (int i = 0; i < ob.getNFe().getInfNFe().getDetalhes().size(); i++) {
				ItemTempXML item = new ItemTempXML();
				item.setDescricao(ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getxProd());
				item.setValor(ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getvProd());
				item.setCodigoProduto(ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getcProd());
				item.setQuantidade(ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getqCom());
				item.setId(i + 1);
				listaItens.add(item);

				System.out.println("Produto :" + ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getcProd());
				System.out.println("Descricao :" + ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getxProd());
				System.out.println("Quantidade :" + ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getqCom());
				System.out.println("Valor :" + ob.getNFe().getInfNFe().getDetalhes().get(i).getcProd().getvProd());
			}
			System.out.println("Chave da Nota:" + ob.getProtNFe().getInfProt().getChNFe());

			for (int j = 0; j < listaItens.size(); j++) {
				listaItens.get(j).setNumeroNota(ob.getNFe().getInfNFe().getIde().getnNF());
				listaItens.get(j).setDataEmissao(ob.getNFe().getInfNFe().getIde().getDhEmi());
				listaItens.get(j).setChaveNota(ob.getProtNFe().getInfProt().getChNFe());
			}

			return listaItens;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}

}
