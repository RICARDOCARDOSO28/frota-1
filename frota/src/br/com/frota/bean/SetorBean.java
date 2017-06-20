package br.com.frota.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.SetorDAO;
import br.com.frota.model.Setor;
import br.com.frota.util.Paginas;

@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Setor setor = new Setor();

	private Integer setorId;

	// Atributos para Consulta
	private String setorNome = "";

	// Listas para Consulta
	private List<Setor> setores;
	private List<Setor> lista = new ArrayList<>();

	// Dao
	private SetorDAO setorDAO;

	public SetorBean() {
		setorDAO = new SetorDAO();
	}

	public Setor getSetor() {
		return setor;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setSetor(Setor setor) {
		this.setor = setor;
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
		this.setorNome = setorNome.trim();
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public Integer getTotalSetores() {
		return setores.size();
	}

	public Integer getTotalSetoresConsulta() {
		return lista.size();
	}

	public List<Setor> getLista() {
		return Collections.unmodifiableList(lista);
	}

	@PostConstruct
	public void createSetores() {
		if (this.setores == null) {
			lista = setorDAO.listarSetoresPorNome(setorNome);
			setores = new ArrayList<>(lista);
		}
		createSetoresConsulta();
	}

	public void createSetoresConsulta() {
		Stream<Setor> filter = lista.stream().filter(s -> (s.getNome().toLowerCase().contains(setorNome.toLowerCase())
				| s.getFone().toLowerCase().contains(setorNome.toLowerCase())));

		setores = filter.collect(Collectors.toList());
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		createSetores();
		return null;
	}

	public String limpar() {
		this.setorNome = "";
		createSetores();
		return null;
	}

	public String gravar() {
		String message = "";
		if (setor.getId() == null) {
			setorDAO.gravar(setor);
			message += "Setor Cadastrado com Sucesso.";
		} else {
			setorDAO.atualizar(setor);
			message += "Setor Atualizado com Sucesso.";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
		System.out.println(setor);
		setor = new Setor();
		return null;
	}

	public void recuperarSetorPorId() {
		setor = setorDAO.buscaSetor(setorId);
	}

	public String novo() {
		return Paginas.CADASTROSETOR;
	}

	public void remover() {
		setorDAO.remover(setor);
		setores = null;
		createSetores();
		setor = null;
	}

	public void remover(Setor setor) {
		this.setor = setor;
		remover();
	}

	public String editar(Setor setor) {
		this.setor = setor;
		return editar();
	}

	public String editar() {
		setorId = this.setor.getId();
		return "setor?setorId=" + setorId;
	}

	public boolean setorIdExiste() {
		if (this.setorId == null) {
			return false;
		}
		return true;
	}

}
