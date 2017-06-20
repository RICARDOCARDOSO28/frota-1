package br.com.frota.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Abastecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "qnt_combustivel", nullable = false)
	private Integer qntCombustivel;

	// @Column(name = "valor", precision = 8, scale = 2, nullable = false)
	// private BigDecimal valor;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "usuario_fk", nullable = false)
	private Usuario usuario;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "veiculo_fk", nullable = false)
	private Veiculo veiculo;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_abastecimento", nullable = false)
	private Calendar dataAbastecimento = Calendar.getInstance();

	@Column(name = "descricao", length = 450)
	private String descricao;

	public Integer getQntCombustivel() {
		return qntCombustivel;
	}

	public void setQntCombustivel(Integer qntCombustivel) {
		this.qntCombustivel = qntCombustivel;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Calendar getDataAbastecimento() {
		return dataAbastecimento;
	}

	public void setDataAbastecimento(Calendar dataAbastecimento) {
		this.dataAbastecimento = dataAbastecimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abastecimento other = (Abastecimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
