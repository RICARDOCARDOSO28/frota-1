package br.com.frota.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.util.StatusAgenda;

@ManagedBean
@ViewScoped
public class UtilBean {

	private Integer valor = 0;

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

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

	public String showDiv() {
		return "show-div";
	}

	public String hiddenDiv() {
		return "hidden-div";
	}

	public void exibirDiv() {
		if (this.valor == 0)
			this.valor = 1;
		else
			this.valor = 0;
	}

}
