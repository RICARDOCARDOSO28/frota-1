package br.com.frota.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesContextUtil{

	public void info(String str, String message) {
		FacesContext.getCurrentInstance().addMessage(str, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}
	
	public void error(String str, String message) {
		FacesContext.getCurrentInstance().addMessage(str, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}
	
	public void warn(String str, String message) {
		FacesContext.getCurrentInstance().addMessage(str, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}
	
	public void fatal(String str, String message) {
		FacesContext.getCurrentInstance().addMessage(str, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
	}

}
