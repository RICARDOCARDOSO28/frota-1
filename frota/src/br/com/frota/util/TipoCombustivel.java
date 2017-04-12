package br.com.frota.util;

public enum TipoCombustivel {

	GASOLINA("Gasolina"), ETANOL("Etano Hidratado"), DIESEL("Diesel"), GAS_VEICULAR("Gás Veicular");

	private String label;

	private TipoCombustivel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
