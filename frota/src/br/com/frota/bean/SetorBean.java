package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.SetorDAO;
import br.com.frota.model.Setor;
import br.com.frota.util.FacesContextUtil;
import br.com.frota.util.Paginas;

@ManagedBean
@ViewScoped
public class SetorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private SetorDAO setorDAO;

	private Setor setor = new Setor();

	private Integer setorId;
	private String setorNome = "";
	private List<Setor> setores;

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

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		return null;
	}

	public void gravar() {
		String message = "";
		if (setor.getId() == null) {
			setorDAO.gravar(setor);
			message += "Setor Cadastrado com Sucesso.";
		} else {
			setorDAO.atualizar(setor);
			message += "Setor Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(null, message);
		setor = new Setor();
	}

	public void recuperarSetorPorId() {
		setor = setorDAO.buscaSetor(setorId);
	}

	public String novo() {
		return Paginas.CADASTROSETOR;
	}

	public void remover() {
		setorDAO.remover(setor);
		setor = null;
	}

	public void remover(Setor setor) {
		setorDAO.remover(setor);
	}

	public String editar(Setor setor) {
		this.setor = setor;
		setorId = setor.getId();
		return "setor?setorId="+setorId;
	}

}
