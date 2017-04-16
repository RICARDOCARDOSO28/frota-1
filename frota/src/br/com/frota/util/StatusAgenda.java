package br.com.frota.util;

public enum StatusAgenda {

	EM_ABERTO("Em Aberto", 0), CANCELADO("Cancelado", 1), ENCERRADO("Encerrado", 2);

	private String label;
	private Integer id;

	private StatusAgenda(String label, Integer id) {
		this.label = label;
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public Integer getId() {
		return id;
	}

}
