package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "itens_nota_fiscal_entrada")
public class ItemNotaEntrada implements BaseEntity, Serializable {

	private static final long serialVersionUID = 8889367985584841345L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "quantidade", precision = 15, scale = 4)
	private BigDecimal quantidade = BigDecimal.ZERO;
	
	@NotNull
	@Column(name = "total_item", precision = 15, scale = 4)
	private BigDecimal totalItem = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "itemPeca_id", nullable = false)
	private Peca itemPeca;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "notaFiscalEntrada_id", nullable = false)
	private NotaFiscalEntrada notaFiscalEntrada;

	@Override
	public Long getId() {
		return this.id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public NotaFiscalEntrada getNotaFiscalEntrada() {
		return notaFiscalEntrada;
	}

	public void setNotaFiscalEntrada(NotaFiscalEntrada notaFiscalEntrada) {
		this.notaFiscalEntrada = notaFiscalEntrada;
	}

	public Peca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(Peca itemPeca) {
		this.itemPeca = itemPeca;
	}

	@Transient
	public BigDecimal getValorTotaItem(){
		return this.itemPeca.getValor().multiply(this.quantidade);
	}

	public BigDecimal getTotalItem() {
		this.setTotalItem(this.getValorTotaItem());
		return this.getValorTotaItem();
	}

	public void setTotalItem(BigDecimal totalItem) {
		this.totalItem = totalItem;
	}
}
