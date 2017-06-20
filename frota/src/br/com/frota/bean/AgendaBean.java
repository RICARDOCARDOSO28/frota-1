package br.com.frota.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
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
public class AgendaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Agenda agenda = new Agenda();

	private Integer agendaId;

	// Atributos de Consulta
	private Integer usuarioId;

	private String agendaNome = "";
	private StatusAgenda statusAgenda;
	private TipoVeiculo tipoVeiculo;
	private Date dataInicial = null;
	private Date dataFinal = Calendar.getInstance().getTime();

	// Listas de Consulta
	private List<Agenda> agendas;
	private List<Agenda> lista = new ArrayList<>();

	// Dao
	private AgendaDAO agendaDAO;

	public AgendaBean() {
		agendaDAO = new AgendaDAO();
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
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

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public List<Agenda> getLista() {
		return Collections.unmodifiableList(lista);
	}

	public Integer getTotalAgendas() {
		return agendas.size();
	}

	public Integer getTotalLista() {
		return lista.size();
	}

	@PostConstruct
	public void createAgendas() {
		if (this.agendas == null) {
			lista.clear();
			agendas = agendaDAO.listarAgenda(agendaNome, dataInicial, dataFinal, tipoVeiculo, statusAgenda);
			lista.addAll(agendas);
		}
		createListaConsulta();
	}

	public void createListaConsulta() {
		Stream<Agenda> consulta = lista.stream();

		if (agendaNome != null)
			consulta = consulta.filter(a -> a.getUsuario().getNome().toLowerCase().contains(agendaNome.toLowerCase()));

		Calendar datanova = Calendar.getInstance();
		if (dataInicial != null) {
			datanova.setTime(dataInicial);
			datanova.add(Calendar.DATE, -1);
			Date time = datanova.getTime();
			consulta = consulta.filter(a -> (a.getDataSaida().getTime().after(time)));
		}

		if (dataFinal != null) {
			datanova.setTime(dataFinal);
			datanova.add(Calendar.DATE, 1);
			Date time = datanova.getTime();
			consulta = consulta.filter(a -> (a.getDataChegada().getTime().before(time)));
		}

		if (tipoVeiculo != null)
			consulta = consulta.filter(a -> (a.getTipoVeiculo().equals(tipoVeiculo)));

		if (statusAgenda != null)
			consulta = consulta.filter(a -> (a.getStatusAgenda().equals(statusAgenda)));

		agendas = consulta.collect(Collectors.toList());
	}

	/**
	 * Método para Iniciar os eventos na página Retorno null
	 */
	public String listar() {
		createAgendas();
		return null;
	}

	public String limpar() {
		this.agendaNome = "";
		this.tipoVeiculo = null;
		this.statusAgenda = null;
		this.dataInicial = null;
		this.dataFinal = Calendar.getInstance().getTime();
		createAgendas();
		return null;
	}

	public void gravar() {
		agendaDAO.gravar(agenda, usuarioId);
		System.out.println(agenda);
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
		this.agenda = agenda;
		remover();
	}

	public void remover() {
		agendaDAO.remover(agenda);
		agendas = null;
		createAgendas();
		agenda = null;
	}

	public String editar() {
		agendaId = agenda.getId();
		usuarioId = agenda.getUsuario().getId();
		return "agenda?agendaId=" + agendaId;
	}

	public String editar(Agenda agenda) {
		this.agenda = agenda;
		return editar();
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
