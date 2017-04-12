package br.com.frota.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.SetorDAO;
import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Setor;
import br.com.frota.model.Usuario;

@ManagedBean
@ViewScoped
public class AdministradorBean {

	private Usuario usuario = new Usuario();
	private Setor setor = new Setor();

	private Integer setorId;

	private UsuarioDAO usuarioDAO;
	private SetorDAO setorDAO;

	public AdministradorBean() {
		usuarioDAO = new UsuarioDAO();
		setorDAO = new SetorDAO();
	}

	public Usuario getUsuario() {
		return usuario;
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

	public String salvar() {
		setorDAO.gravar(setor);
		usuario.setTipoUsuario(0); //SuperUsuario
		usuarioDAO.adicionar(usuario, setor.getId());
		FacesContext.getCurrentInstance().addMessage("usuario",
				new FacesMessage("Usuario " + usuario.getNome() + " Cadastrado com Sucesso"));
		usuario = new Usuario();
		return "login?faces-redirect=true";
	}
}
