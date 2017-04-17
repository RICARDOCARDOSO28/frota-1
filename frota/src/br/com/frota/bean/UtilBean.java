package br.com.frota.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.util.StatusAgenda;

@ManagedBean
@ViewScoped
public class UtilBean {

	public String statusColor(StatusAgenda status) {
		if (status == StatusAgenda.EM_ABERTO) {
			return "bg-green";
		}
		if (status == StatusAgenda.CANCELADO) {
			return "bg-yellow";
		}
		if (status == StatusAgenda.ENCERRADO) {
			return "bg-blue";
		}
		return "bg-red";
	}

}
