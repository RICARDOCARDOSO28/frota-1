package br.com.frota.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.AgendaDAO;
import br.com.frota.model.Agenda;
import br.com.frota.model.Usuario;

@ManagedBean
@ViewScoped
public class AgendaBean {

	private AgendaDAO agendaDAO;

	private Agenda agenda = new Agenda();
	private Integer agendaId;
	private List<Agenda> agendas;
	private String agendaNome;

	private Integer usuarioId;

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
		agendas = agendaDAO.listarAgenda();
		return agendas;
	}
	
	public List<Agenda> getAgendasPorNome(String nome) {
		agendas = agendaDAO.listarAgendaPorNome(nome);
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

	public void gravar() {
		System.out.println(usuarioId);
		System.out.println(agenda.getTipoVeiculo());
		
		agendaDAO.gravar(agenda, usuarioId);
		agenda = new Agenda();
	}

	public Agenda recuperarAgendaPorId() {
		return agendaDAO.recuperarAgendaPorId(agendaId);
	}
	
	public String carregarUsuario(Usuario usuario){
		usuarioId = usuario.getId();
		return null;
	}
}
