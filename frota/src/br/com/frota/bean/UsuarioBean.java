package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Usuario;
import br.com.frota.util.TipoSexo;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private Integer usuarioId;
	private String usuarioNome = "";
	private List<Usuario> usuarios;
	private List<Usuario> motoristas;
	private List<Usuario> administradores;

	private Integer setorId;

	private UsuarioDAO usuarioDAO;

	public UsuarioBean() {
		usuarioDAO = new UsuarioDAO();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public Integer getSetorId() {
		return setorId;
	}

	public void setSetorId(Integer setorId) {
		this.setorId = setorId;
	}

	public List<Usuario> getUsuarios() {
		usuarios = usuarioDAO.listarUsuariosPorNome(usuarioNome);
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getMotoristas() {
		motoristas = usuarioDAO.listarMotoristas();
		return motoristas;
	}

	public void setMotoristas(List<Usuario> motoristas) {
		this.motoristas = motoristas;
	}

	public List<Usuario> getAdministradores() {
		administradores = usuarioDAO.listarAdministradores();
		return administradores;
	}

	public void setAdministradores(List<Usuario> administradores) {
		this.administradores = administradores;
	}

	public TipoSexo[] getTipoSexo() {
		return TipoSexo.values();
	}

	/*
	 * Metodos CRUD
	 */

	public String listarPorNome() {
		listarUsuariosPorFiltros();
		return null;
	}

	public void gravar() {
		if (usuario.getId() == null) {
			usuario.setTipoUsuario(2);
			usuarioDAO.adicionar(usuario, setorId);
			FacesContext.getCurrentInstance().addMessage("usuario", new FacesMessage("Usuario Salvo com Sucesso"));
		} else {
			usuarioDAO.atualizar(usuario, setorId);
			FacesContext.getCurrentInstance().addMessage("usuario", new FacesMessage("Usuario Atualizado com Sucesso"));
		}
		usuario = new Usuario();
		return;
	}

	public void remover(Usuario usuario) {
		usuarioDAO.remover(usuario);
	}

	public void recuperarUsuarioPorId() {
		usuario = usuarioDAO.buscarUsuario(usuarioId);
	}

	public String editar(Usuario usuario) {
		this.usuario = usuario;
		this.setorId = usuario.getSetor().getId();
		return "usuario?faces-redirect=true";
	}

	public String novoUsuario() {
		return "usuario?faces-redirect=true";
	}

	public void listarUsuariosPorFiltros() {
		usuarios = new UsuarioDAO().listarUsuariosPorFiltros(usuarioNome, setorId);
	}

	// public void confereSenha(FaceletContext fc, UIComponent component, Object
	// value) throws ValidationException {
	// String senhaConfere = value.toString();
	// if (!senhaConfere.equals(senha)) {
	// throw new ValidatorException(new FacesMessage("Senha não Confere"));
	// }
	// }

	
	public String carregarUsuario(Usuario usuario){
		this.usuario = usuario;
		usuarioNome = this.usuario.getNome();
		return null;
	}
}
