package br.com.frota.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.ControleCirculacaoDAO;
import br.com.frota.model.ControleCirculacao;
import br.com.frota.util.Paginas;

@ManagedBean
@ViewScoped
public class ControleCirculacaoBean {

	private ControleCirculacaoDAO controleDAO;

	private ControleCirculacao controle = new ControleCirculacao();
	private Integer controleId;
	private List<ControleCirculacao> controles;

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

	public List<ControleCirculacao> getControles() {
		controles = controleDAO.listarControle(usuario, veiculoId, motoristaId, veiculo, destino, motorista,
				dataInicial, dataFinal);
		return controles;
	}

	public void setControles(List<ControleCirculacao> controles) {
		this.controles = controles;
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

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		return null;
	}

	public void gravar() {
		controleDAO.gravar(controle, agendaId, veiculoId, motoristaId);
		controle = new ControleCirculacao();
	}

	public ControleCirculacao recuperarControlePorId() {
		return controleDAO.recuperarControleCirculacaoPorId(controleId);
	}

	public void remover(ControleCirculacao controle) {
		controleDAO.remover(controle);
	}

	public void remover() {
		controleDAO.remover(controle);
		controle = null;
	}

	public String editar(ControleCirculacao controle) {
		this.controle = controle;
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
