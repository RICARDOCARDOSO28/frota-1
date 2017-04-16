package br.com.frota.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.frota.dao.UsuarioDAO;
import br.com.frota.model.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuarLogin() {
		System.out.println("efetuar login: " + usuario.getCpf());

		FacesContext context = FacesContext.getCurrentInstance();

		boolean usuarioExiste = new UsuarioDAO().usuarioExiste(usuario);

		// Se usuario existir loga no sitema
		if (usuarioExiste) {
			context.getExternalContext().getSessionMap().put("usuariologado", usuario);
			return "index?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuario não Existe"));
		return "login?faces-redirect=true";

	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuariologado");
		return "login?faces-redirect=true";
	}
}
