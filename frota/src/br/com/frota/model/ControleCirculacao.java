//package br.com.frota.model;
//
//import java.util.Calendar;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//@Entity
//public class ControleCirculacao {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "agenda_fk")
//	private Agenda agenda;
//
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "veiculo_fk")
//	private Veiculo veiculo;
//
//	@Temporal(TemporalType.DATE)
//	private Calendar dataSaidaReal = Calendar.getInstance();
//
//	@Temporal(TemporalType.DATE)
//	private Calendar dataChegadaReal = Calendar.getInstance();
//
//	@Temporal(TemporalType.TIME)
//	private Calendar horaSaidaReal = Calendar.getInstance();
//
//	@Temporal(TemporalType.TIME)
//	private Calendar horaChegadaReal = Calendar.getInstance();
//
//	@Column(name = "odometro_saida", nullable = false)
//	private double odometroSaida;
//
//	@Column(name = "odometro_chegada", nullable = false)
//	private double odometroChegada;
//
//	@Column(name = "destino", length = 300)
//	private String destino;
//
//	@Column(name = "descricao", length = 450)
//	private String descricao;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public Agenda getAgenda() {
//		return agenda;
//	}
//
//	public void setAgenda(Agenda agenda) {
//		this.agenda = agenda;
//	}
//
//	public Veiculo getVeiculo() {
//		return veiculo;
//	}
//
//	public void setVeiculo(Veiculo veiculo) {
//		this.veiculo = veiculo;
//	}
//
//	public Calendar getDataSaidaReal() {
//		return dataSaidaReal;
//	}
//
//	public void setDataSaidaReal(Calendar dataSaidaReal) {
//		this.dataSaidaReal = dataSaidaReal;
//	}
//
//	public Calendar getDataChegadaReal() {
//		return dataChegadaReal;
//	}
//
//	public void setDataChegadaReal(Calendar dataChegadaReal) {
//		this.dataChegadaReal = dataChegadaReal;
//	}
//
//	public Calendar getHoraSaidaReal() {
//		return horaSaidaReal;
//	}
//
//	public void setHoraSaidaReal(Calendar horaSaidaReal) {
//		this.horaSaidaReal = horaSaidaReal;
//	}
//
//	public Calendar getHoraChegadaReal() {
//		return horaChegadaReal;
//	}
//
//	public void setHoraChegadaReal(Calendar horaChegadaReal) {
//		this.horaChegadaReal = horaChegadaReal;
//	}
//
//	public double getOdometroSaida() {
//		return odometroSaida;
//	}
//
//	public void setOdometroSaida(double odometroSaida) {
//		this.odometroSaida = odometroSaida;
//	}
//
//	public double getOdometroChegada() {
//		return odometroChegada;
//	}
//
//	public void setOdometroChegada(double odometroChegada) {
//		this.odometroChegada = odometroChegada;
//	}
//
//	public String getDestino() {
//		return destino;
//	}
//
//	public void setDestino(String destino) {
//		this.destino = destino;
//	}
//
//	public String getDescricao() {
//		return descricao;
//	}
//
//	public void setDescricao(String descricao) {
//		this.descricao = descricao;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ControleCirculacao other = (ControleCirculacao) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}
//}
