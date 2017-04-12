package br.com.frota.util;

public enum TipoSexo {

	M("Masculino"),F("Feminino");
	
	private String label;

    private TipoSexo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
