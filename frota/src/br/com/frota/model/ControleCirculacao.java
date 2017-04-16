package br.com.frota.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.frota.util.StatusAgenda;

@Entity
public class ControleCirculacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "agenda_fk")
	private Agenda agenda;

	@ManyToOne
	@JoinColumn(name = "veiculo_fk")
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "motorista_fk")
	private Usuario motorista;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_saida", nullable = false)
	private Calendar dataSaida = Calendar.getInstance();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_chegada", nullable = false)
	private Calendar dataChegada = Calendar.getInstance();

	@Column(name = "hora_saida", nullable = false, length = 5)
	private String horaSaida;

	@Column(name = "hora_chegada", nullable = false, length = 5)
	private String horaChegada;

	@Column(name = "odometro_saida", nullable = false, length = 8)
	private Double odometroSaida;

	@Column(name = "odometro_chegada", nullable = false, length = 8)
	private Double odometroChegada;

	@Column(name = "km_rodados")
	private Double kmRodados;

	@Column(name = "destino")
	private String destino;

	@Column(name = "descricao", length = 450)
	private String descricao;

	public Integer getId() {
		return id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getMotorista() {
		return motorista;
	}

	public void setMotorista(Usuario motorista) {
		this.motorista = motorista;
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

	public Double getOdometroSaida() {
		return odometroSaida;
	}

	public void setOdometroSaida(Double odometroSaida) {
		this.odometroSaida = odometroSaida;
	}

	public Double getOdometroChegada() {
		return odometroChegada;
	}

	public void setOdometroChegada(Double odometroChegada) {
		this.odometroChegada = odometroChegada;
	}
	
	public Double getKmRodados() {
		return kmRodados;
	}

	public void setKmRodados(Double kmRodados) {
		this.kmRodados = kmRodados;
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
		ControleCirculacao other = (ControleCirculacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
