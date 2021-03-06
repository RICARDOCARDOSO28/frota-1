package br.com.frota.util;

public enum TipoUsuario {

	SUPERUSER("Super Usuario", 0), 
	ADMINISTRADOR("Administrador", 2), 
	USUARIO("Usuario Comum", 5);

	private String label;
	private Integer id;

	private TipoUsuario(String label, Integer id) {
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
