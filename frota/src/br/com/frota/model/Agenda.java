package br.com.frota.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.frota.util.TipoVeiculo;

@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_fk", nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_saida", nullable = false)
	private Calendar dataSaida = Calendar.getInstance();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_chegada")
	private Calendar dataChegada = Calendar.getInstance();

//	@Temporal(TemporalType.TIME)
//	@Column(name = "hora_saida", nullable = false)
//	private Date horaSaida;
//
//	@Temporal(TemporalType.TIME)
//	@Column(name = "hora_chegada")
//	private Date horaChegada;
	
	@Column(name = "hora_saida", nullable = false, length = 30)
	private String horaSaida;

	@Column(name = "hora_chegada", length = 30)
	private String horaChegada;
	

	@Column(name = "tipo_veiculo", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private TipoVeiculo tipoVeiculo;

	@Column(name = "destino", length = 300)
	private String destino;

	@Column(name = "descricao", length = 450)
	private String descricao;

	// @OneToMany(mappedBy = "agenda")
	// private List<ControleCirculacao> controlesCirculacao = new ArrayList<>();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Calendar getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Calendar dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Calendar getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Calendar dataChegada) {
		this.dataChegada = dataChegada;
	}

	public String getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(String horaSaida) {
		this.horaSaida = horaSaida;
	}

	public String getHoraChegada() {
		return horaChegada;
	}

	public void setHoraChegada(String horaChegada) {
		this.horaChegada = horaChegada;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	// public List<ControleCirculacao> getControlesCirculacao() {
	// return controlesCirculacao;
	// }
	//
	// public void setControlesCirculacao(List<ControleCirculacao>
	// controlesCirculacao) {
	// this.controlesCirculacao = controlesCirculacao;
	// }

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Agenda[id:" + getId() + ", dataSaida:" + getDataSaida() + ", dataChegada:" + getDataChegada()
				+ ", horaSaida:" + getHoraSaida() + ", horaChegada:" + getHoraChegada() + ", tipoVeiculo:"
				+ getTipoVeiculo() + ", destino:" + getDestino() + ", descricao:" + getDescricao() + "]\n\t"
				+ getUsuario().toString();
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
		Agenda other = (Agenda) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
