package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Usuario;
import br.com.frota.util.FacesContextUtil;
import br.com.frota.util.Paginas;
import br.com.frota.util.TipoSexo;
import br.com.frota.util.TipoUsuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private Integer usuarioId;
	private String usuarioNome;
	private TipoUsuario tipoUsuario;
	private List<Usuario> usuarios;
	private List<Usuario> motoristas;

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

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<Usuario> getUsuarios() {
		usuarios = usuarioDAO.listarUsuariosPorNome(usuarioNome, setorId, tipoUsuario);
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getMotoristas() {
		motoristas = usuarioDAO.listarUsuariosPorNome(null, null, tipoUsuario);
		return motoristas;
	}

	public void setMotoristas(List<Usuario> motoristas) {
		this.motoristas = motoristas;
	}

	public void setUsuariosPorSetor(Integer setorId) {
		this.setorId = setorId;
	}

	public TipoSexo[] getTipoSexo() {
		return TipoSexo.values();
	}
	
	public TipoUsuario[] getTipoUsuarios() {
		return TipoUsuario.values();
	}
	

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		return null;
	}

	public void gravar() {
		System.out.println("Usuario [id:" + usuario.getId() + ", nome:" + usuario.getNome() + "]");
		System.out.println("Setor [setorId:" + setorId);
		if (usuario.getId() == null) {
			usuario.setTipoUsuario(TipoUsuario.USUARIO);
			usuarioDAO.adicionar(usuario, setorId);
			new FacesContextUtil().info(null, "Usuario Salvo com Sucesso");
		} else {
			usuarioDAO.atualizar(usuario, setorId);
			new FacesContextUtil().info(null, "Usuario Atualizado com Sucesso");
		}
		usuario = new Usuario();
		return;
	}

	public void remover() {
		usuarioDAO.remover(usuario);
		usuario = null;
	}

	public void remover(Usuario usuario) {
		usuarioDAO.remover(usuario);
	}

	public void recuperarUsuarioPorId() {
		usuario = usuarioDAO.buscarUsuario(usuarioId);
	}

	public String editar(Usuario usuario) {
		this.usuario = usuario;
		usuarioId = usuario.getId();
		setorId = usuario.getSetor().getId();
		return "usuario?usuarioId=" + usuarioId;
	}

	public String novo() {
		return Paginas.CADASTROUSUARIO;
	}

	// public void confereSenha(FaceletContext fc, UIComponent component, Object
	// value) throws ValidationException {
	// String senhaConfere = value.toString();
	// if (!senhaConfere.equals(senha)) {
	// throw new ValidatorException(new FacesMessage("Senha não Confere"));
	// }
	// }

	public String carregarUsuario(Usuario usuario) {
		this.usuario = usuario;
		usuarioId = usuario.getId();
		usuarioNome = usuario.getNome();
		setorId = usuario.getSetor().getId();
		tipoUsuario = usuario.getTipoUsuario();
		return null;
	}

}
