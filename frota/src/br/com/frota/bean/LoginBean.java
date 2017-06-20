package br.com.frota.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private Usuario userlogado;

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUserlogado() {
		return userlogado;
	}

	public String efetuarLogin() {
		System.out.println("efetuar login: " + usuario.getCpf());

		FacesContext context = FacesContext.getCurrentInstance();

		// boolean usuarioExiste = new UsuarioDAO().usuarioExiste(usuario);
		Usuario user = new UsuarioDAO().usuarioExiste(usuario);

		// Se usuario existir loga no sistema
		if (user != null) {
			context.getExternalContext().getSessionMap().put("usuariologado", usuario);
			this.userlogado = user;
			return "index?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuario não Existe"));
		return "login?faces-redirect=true";

	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuariologado");
		this.userlogado = null;
		return "login";
	}
}
