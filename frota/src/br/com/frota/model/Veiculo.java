package br.com.frota.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.frota.util.TipoCombustivel;
import br.com.frota.util.TipoVeiculo;

@Entity
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "placa", length = 7, nullable = false, unique = true)
	private String placa;

	@Column(name = "marca", nullable = false, length = 30)
	private String marca;

	@Column(name = "modelo", nullable = false, length = 30)
	private String modelo;

	@Column(name = "ano", nullable = false, length = 4)
	private Integer ano;

	@Column(name = "odometro", nullable = false)
	private double odometro;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_veiculo", nullable = false, length = 30)
	private TipoVeiculo tipoVeiculo;
	
	@Column(name = "tipo_combustivel", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private TipoCombustivel tipoCombustivel;
	
	@OneToMany(mappedBy = "veiculo", cascade=CascadeType.MERGE)
	private List<Abastecimento> abastecimentos = new ArrayList<>();

//	@OneToMany(mappedBy = "veiculo")
//	private List<ControleCirculacao> controlesCirculacao = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public double getOdometro() {
		return odometro;
	}

	public void setOdometro(double odometro) {
		this.odometro = odometro;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

//	public List<ControleCirculacao> getControlesCirculacao() {
//		return controlesCirculacao;
//	}
//
//	public void setControlesCirculacao(List<ControleCirculacao> controlesCirculacao) {
//		this.controlesCirculacao = controlesCirculacao;
//	}

	public TipoCombustivel getTipoCombustivel() {
		return tipoCombustivel;
	}

	public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}

	@Override
	public String toString() {
		return "Veiculo[id:" + getId() + ", placa:" + getPlaca() + ", marca:" + getMarca() + ", modelo:" + getModelo()
				+ ", ano:" + getAno() + ", odometro:" + getOdometro() + ", tipoVeiculo:" + getTipoVeiculo() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Veiculo other = (Veiculo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
