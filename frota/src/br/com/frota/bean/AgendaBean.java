package br.com.frota.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.AgendaDAO;
import br.com.frota.model.Agenda;
import br.com.frota.model.Usuario;
import br.com.frota.util.Paginas;
import br.com.frota.util.StatusAgenda;
import br.com.frota.util.TipoVeiculo;

@ManagedBean
@ViewScoped
public class AgendaBean {

	private AgendaDAO agendaDAO;

	private Agenda agenda = new Agenda();
	private Integer agendaId;
	private String agendaNome;
	private List<Agenda> agendas;

	private Integer usuarioId;

	private StatusAgenda statusAgenda;
	private TipoVeiculo tipoVeiculo;
	private Date dataInicial = null;
	private Date dataFinal = Calendar.getInstance().getTime();

	public AgendaBean() {
		agendaDAO = new AgendaDAO();
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public Integer getAgendaId() {
		return agendaId;
	}

	public void setAgendaId(Integer agendaId) {
		this.agendaId = agendaId;
	}

	public String getAgendaNome() {
		return agendaNome;
	}

	public void setAgendaNome(String agendaNome) {
		this.agendaNome = agendaNome;
	}

	public List<Agenda> getAgendas() {
		agendas = agendaDAO.listarAgenda(agendaNome, dataInicial, dataFinal, tipoVeiculo, statusAgenda);
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public StatusAgenda getStatusAgenda() {
		return statusAgenda;
	}

	public void setStatusAgenda(StatusAgenda statusAgenda) {
		this.statusAgenda = statusAgenda;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
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

	public StatusAgenda[] getStatusAgendas() {
		return StatusAgenda.values();
	}

	public TipoVeiculo[] getTipoVeiculos() {
		return TipoVeiculo.values();
	}

	/**
	 * Método para Iniciar os eventos na página Retorno null
	 */
	public String listar() {
		return null;
	}

	public void gravar() {
		agendaDAO.gravar(agenda, usuarioId);
		agenda = new Agenda();
	}

	public void atualizar() {
		agendaDAO.atualizar(agenda, usuarioId, statusAgenda);
		agenda = new Agenda();
	}

	public Agenda recuperarAgendaPorId() {
		agenda = agendaDAO.recuperarAgendaPorId(agendaId);
		return agenda;
	}

	public String carregarUsuario(Usuario usuario) {
		usuarioId = usuario.getId();
		return null;
	}

	public void remover(Agenda agenda) {
		agendaDAO.remover(agenda);
	}

	public void remover() {
		agendaDAO.remover(agenda);
		agenda = null;
	}

	public String editar(Agenda agenda) {
		this.agenda = agenda;
		agendaId = agenda.getId();
		usuarioId = agenda.getUsuario().getId();
		return "agenda?agendaId=" + agendaId;
	}

	public String novo() {
		return Paginas.CADASTROAGENDA;
	}

	public String agendaControle(Agenda agenda) {
		this.agenda = agenda;
		agendaId = agenda.getId();
		return "controle?agendaId=" + agendaId;
	}

	public String cancelarAgenda() {
		usuarioId = agenda.getUsuario().getId();
		agendaDAO.atualizar(agenda, usuarioId, StatusAgenda.CANCELADO);
		return null;
	}

	public void encerrarAgenda() {
		this.statusAgenda = StatusAgenda.ENCERRADO;
		usuarioId = agenda.getUsuario().getId();
	}
}
