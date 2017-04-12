package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.VeiculoDAO;
import br.com.frota.model.Veiculo;
import br.com.frota.util.TipoCombustivel;
import br.com.frota.util.TipoVeiculo;

@ManagedBean
@ViewScoped
public class VeiculoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private VeiculoDAO veiculoDAO;

	private Veiculo veiculo = new Veiculo();
	private Integer veiculoId;
	private String veiculoPlaca = "";
	private List<Veiculo> veiculos;

	public VeiculoBean() {
		veiculoDAO = new VeiculoDAO();
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

	public String getVeiculoPlaca() {
		return veiculoPlaca;
	}

	public void setVeiculoPlaca(String veiculoPlaca) {
		this.veiculoPlaca = veiculoPlaca;
	}

	public List<Veiculo> getVeiculos() {
		veiculos = veiculoDAO.listarVeiculosPorNome(veiculoPlaca);
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public TipoVeiculo[] getTipoVeiculo() {
		return TipoVeiculo.values();
	}
	
	public TipoCombustivel[] getTipoCombustivel() {
		return TipoCombustivel.values();
	}

	/*
	 * CRUD
	 */

	public void gravar() {
		veiculoDAO.gravar(veiculo);
		veiculo = new Veiculo();
	}

	public void recuperarVeiculoPorId() {
		veiculo = veiculoDAO.recuperarVeiculoPorId(veiculoId);
	}
	
	public String listarPorNome() {
		return null;
	}
	
	public String novoVeiculo() {
		return "veiculo?faces-redirect=true";
	}
	
	
	public void remover(Veiculo veiculo) {
		veiculoDAO.remover(veiculo);
	}

	public String editar(Veiculo veiculo) {
		this.veiculo = veiculo;
		return "veiculo?faces-redirect=true";
	}

}
