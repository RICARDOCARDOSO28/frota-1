package br.com.frota.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.ControleCirculacaoDAO;
import br.com.frota.model.ControleCirculacao;
import br.com.frota.util.Paginas;

@ManagedBean
@ViewScoped
public class ControleCirculacaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private ControleCirculacaoDAO controleDAO;

	private ControleCirculacao controle = new ControleCirculacao();
	private Integer controleId;

	private List<ControleCirculacao> controles;
	private List<ControleCirculacao> lista = new ArrayList<>();

	private Integer motoristaId;
	private Integer agendaId;
	private Integer veiculoId;

	// Variaveis para consulta
	private String usuario;
	private String motorista;
	private String veiculo;

	private String destino;
	private Date dataInicial = null;
	private Date dataFinal = Calendar.getInstance().getTime();

	public ControleCirculacaoBean() {
		controleDAO = new ControleCirculacaoDAO();
	}

	public ControleCirculacao getControle() {
		return controle;
	}

	public void setControle(ControleCirculacao controle) {
		this.controle = controle;
	}

	public Integer getControleId() {
		return controleId;
	}

	public void setControleId(Integer controleId) {
		this.controleId = controleId;
	}

	public Integer getMotoristaId() {
		return motoristaId;
	}

	public void setMotoristaId(Integer motoristaId) {
		this.motoristaId = motoristaId;
	}

	public Integer getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(Integer agendaId) {
		this.agendaId = agendaId;
	}

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<ControleCirculacao> getControles() {
		return controles;
	}

	public List<ControleCirculacao> getLista() {
		return lista;
	}

	public Integer getTotalControles() {
		return controles.size();
	}

	public Integer getTotalLista() {
		return lista.size();
	}

	@PostConstruct
	public void createControles() {
		if (this.controles == null) {
			lista = controleDAO.listarControle(usuario, veiculoId, motoristaId, veiculo, destino, motorista,
					dataInicial, dataFinal);
			controles = new ArrayList<>(lista);
		}
		createLista();
	}

	public void createLista() {
		Stream<ControleCirculacao> consulta = lista.stream();

		if (usuario != null)
			consulta = consulta
					.filter(c -> c.getAgenda().getUsuario().getNome().toLowerCase().contains(usuario.toLowerCase()));

		if (motorista != null)
			consulta = consulta.filter(c -> c.getMotorista().getNome().toLowerCase().contains(motorista.toLowerCase()));

		if (destino != null)
			consulta = consulta.filter(c -> c.getDestino().toLowerCase().contains(destino.toLowerCase()));

		Calendar datanova = Calendar.getInstance();
		if (dataInicial != null) {
			datanova.setTime(dataInicial);
			datanova.add(Calendar.DATE, -1);
			Date time = datanova.getTime();
			consulta = consulta.filter(c -> (c.getDataSaida().getTime().after(time)));
		}

		if (dataFinal != null) {
			datanova.setTime(dataFinal);
			datanova.add(Calendar.DATE, 1);
			Date time = datanova.getTime();
			consulta = consulta.filter(c -> (c.getDataChegada().getTime().before(time)));
		}

		if (veiculo != null) {
			consulta = consulta
					.filter(c -> (c.getVeiculo().getPlaca().toLowerCase().contains(veiculo.toLowerCase()))
							|| c.getVeiculo().getMarca().toLowerCase().contains(veiculo.toLowerCase())
							|| c.getVeiculo().getModelo().toLowerCase().contains(veiculo.toLowerCase()));
		}

		if (motoristaId != null)
			consulta = consulta.filter(c -> (c.getMotorista().getId().equals(motoristaId)));

		controles = consulta.collect(Collectors.toList());
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		createControles();
		return null;
	}

	public String limpar() {
		usuario = "";
		motorista = "";
		veiculo = "";
		destino = "";
		dataInicial = null;
		dataFinal = Calendar.getInstance().getTime();
		return listar();
	}

	public void gravar() {
		controleDAO.gravar(controle, agendaId, veiculoId, motoristaId);
		System.out.println(controle);
		controle = new ControleCirculacao();
	}

	public ControleCirculacao recuperarControlePorId() {
		return controleDAO.recuperarControleCirculacaoPorId(controleId);
	}

	public void remover(ControleCirculacao controle) {
		this.controle = controle;
		remover();
	}

	public void remover() {
		controleDAO.remover(controle);
		controles = null;
		createControles();
		controle = null;
	}

	public String editar(ControleCirculacao controle) {
		this.controle = controle;
		return editar();
	}

	public String editar() {
		controleId = controle.getId();
		return "controle?controleId=" + controleId;
	}

	public String novo() {
		return Paginas.CADASTROCONTROLE;
	}

	public void carregarAgenda(Integer agendaId) {
		System.out.println("agendaId= " + agendaId);
		this.agendaId = agendaId;
	}
}
