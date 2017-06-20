package br.com.frota.util;

public enum StatusAgenda {

	EM_ABERTO("Em Aberto"), CANCELADO("Cancelado"), ENCERRADO("Encerrado");

	private String label;

	private StatusAgenda(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
