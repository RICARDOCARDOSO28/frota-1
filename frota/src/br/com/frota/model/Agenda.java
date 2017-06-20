package br.com.frota.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.frota.util.StatusAgenda;
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

	@Column(name = "hora_saida", nullable = false, length = 5)
	private String horaSaida;

	@Column(name = "hora_chegada", length = 5)
	private String horaChegada;

	@Column(name = "tipo_veiculo", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private TipoVeiculo tipoVeiculo;

	@Column(name = "status_agenda", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private StatusAgenda statusAgenda;

	@Column(name = "destino", length = 300)
	private String destino;

	@Column(name = "descricao", length = 450)
	private String descricao;

	@OneToMany(mappedBy = "agenda")
	private List<ControleCirculacao> controlesCirculacao = new ArrayList<>();

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

	public StatusAgenda getStatusAgenda() {
		return statusAgenda;
	}

	public void setStatusAgenda(StatusAgenda statusAgenda) {
		this.statusAgenda = statusAgenda;
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

	public List<ControleCirculacao> getControlesCirculacao() {
		return controlesCirculacao;
	}

	public void setControlesCirculacao(List<ControleCirculacao> controlesCirculacao) {
		this.controlesCirculacao = controlesCirculacao;
	}

	public Integer getId() {
		return id;
	}

	public void adicionarControle(ControleCirculacao c) {
		c.setAgenda(this);
		getControlesCirculacao().add(c);
	}

	public void removerControle(ControleCirculacao c) {
		c.setAgenda(null);
		getControlesCirculacao().remove(c);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Agenda [");
		builder.append("\n\tid :" + getId());
		builder.append("\n\tdataSaida:" + getDataSaida().getTime());
		builder.append("\n\tdataChegada:" + getDataChegada().getTime());
		builder.append("\n\thoraSaida:" + getHoraSaida());
		builder.append("\n\thoraChegada:" + getHoraChegada());
		builder.append("\n\ttipoVeiculo:" + getTipoVeiculo());
		builder.append("\n\tdestino:" + getDestino());
		builder.append("\n\tdescricao:" + getDescricao());
		builder.append("\n]");
		return builder.toString();
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
