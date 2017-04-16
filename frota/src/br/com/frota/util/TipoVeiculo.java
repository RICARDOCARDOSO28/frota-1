package br.com.frota.util;

public enum TipoVeiculo {

	PASSEIO("Passeio"),
	PICKUP("Pickup"),
	VAN("Van Utilitario"),
	MICROONIBUS("Micro-onibus"),
	ONIBUS("Onibus"),
	CAMINHONETE("Caminhonete"),
	CAMINHAO("Caminhão"),
	TRATOR("Trator");
	
	private String label;
	
	private TipoVeiculo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
