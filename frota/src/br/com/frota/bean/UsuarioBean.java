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

import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Usuario;
import br.com.frota.util.Paginas;
import br.com.frota.util.TipoSexo;
import br.com.frota.util.TipoUsuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private Integer usuarioId;

	// Atributos para Consulta
	private String usuarioNome = "";
	private Integer setorId;
	private TipoUsuario tipoUsuario;

	// Listas de Consulta
	private List<Usuario> usuarios;
	private List<Usuario> usuariosConsulta = new ArrayList<>();

	// decidir se removo ou não
	private List<Usuario> motoristas;

	// Dao
	private UsuarioDAO usuarioDAO;

	public UsuarioBean() {
		usuarioDAO = new UsuarioDAO();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public List<Usuario> getUsuariosConsulta() {
		return Collections.unmodifiableList(usuariosConsulta);
	}

	@PostConstruct
	public void createUsuarios() {
		if (this.usuarios == null) {
			usuariosConsulta.clear();
			usuarios = usuarioDAO.listarUsuariosPorNome(usuarioNome, setorId, tipoUsuario);
			usuariosConsulta.addAll(usuarios);
		}
		createUsuariosConsulta();
	}

	public void createUsuariosConsulta() {
		Stream<Usuario> consulta = usuariosConsulta.stream();

		if (usuarioNome != null)
			consulta = consulta.filter(u -> u.getNome().toLowerCase().contains(usuarioNome.toLowerCase()));

		if (setorId != null)
			consulta = consulta.filter(u -> (u.getSetor().getId().equals(setorId)));

		if (tipoUsuario != null)
			consulta = consulta.filter(u -> (u.getTipoUsuario().equals(tipoUsuario)));

		usuarios = consulta.collect(Collectors.toList());
	}

	public Integer getTotalUsuarios() {
		return usuarios.size();
	}

	public Integer getTotalUsuariosConsulta() {
		return usuariosConsulta.size();
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		createUsuarios();
		return null;
	}

	public String limpar() {
		this.usuarioNome = "";
		this.tipoUsuario = null;
		this.setorId = null;
		createUsuarios();
		return null;
	}

	public String gravar() {
		validarCNH();
		if (usuario.getId() == null) {
			usuario.setTipoUsuario(TipoUsuario.USUARIO);
			usuarioDAO.adicionar(usuario, setorId);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Salvo com Sucesso", null));
		} else {
			usuarioDAO.atualizar(usuario, setorId);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Atualizado com Sucesso", null));
		}
		System.out.println(usuario.toString());
		usuario = new Usuario();
		return null;
	}

	private void validarCNH() {
		if (usuario.getCnhId().length() < 11) {
			usuario.setCnhId(null);
			usuario.setCnhValidade(null);
		}
	}

	public void remover() {
		usuarioDAO.remover(usuario);
		usuarios = null;
		createUsuarios();
		usuario = null;
	}

	public void remover(Usuario usuario) {
		this.usuario = usuario;
		remover();
	}

	public void recuperarUsuarioPorId() {
		usuario = usuarioDAO.buscarUsuario(usuarioId);
	}

	public String editar(Usuario usuario) {
		this.usuario = usuario;
		return editar();
	}

	public String editar() {
		usuarioId = usuario.getId();
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
