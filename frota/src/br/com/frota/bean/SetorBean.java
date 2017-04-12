package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.SetorDAO;
import br.com.frota.model.Setor;

@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private SetorDAO setorDAO;

	private Setor setor = new Setor();

	private List<Setor> setores;
	private Integer setorId;
	private String setorNome = "";

	public SetorBean() {
		setorDAO = new SetorDAO();
	}

	public Setor getSetor() {
		return setor;
	}

	public Integer getSetorId() {
		return setorId;
	}

	public void setSetorId(Integer setorId) {
		this.setorId = setorId;
	}

	public String getSetorNome() {
		return setorNome;
	}

	public void setSetorNome(String setorNome) {
		this.setorNome = setorNome;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<Setor> getSetores() {
		setores = setorDAO.listarSetoresPorNome(setorNome);
		return setores;
	}

	/*
	 * Métodos Sem retorno void - CRUD
	 */

	// Create
	public void gravar() {
		setorDAO.gravar(setor);
		info();
		setor = new Setor();
	}

	// Read
	public void recuperarSetorPorId() {
		setor = setorDAO.buscaSetor(setorId);
	}

	// Update
	public void atualizar() {
		setorDAO.atualizar(setor);
	}

	// Delete
	public void remover() {
		setorDAO.remover(setor);
		setor = null;
	}

	public void remover(Setor setor) {
		setorDAO.remover(setor);
	}

	public void encerrar() {
		setorDAO.encerrar();
	}

	public String novoUsuario() {
		return "setor?faces-redirect=true";
	}

	public String listarPorNome() {
		return null;
	}

	public String editar(Setor setor) {
		this.setor = setor;
		return "setor?setorId=" + setorId;
	}

	public void info() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Setor Cadastrado com Sucesso.",
				"" + setor.getNome() + " , Telefone: " + setor.getFone() + ", Email: " + setor.getEmail() + "."));
	}

}
