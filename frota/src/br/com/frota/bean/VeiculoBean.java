package br.com.frota.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.VeiculoDAO;
import br.com.frota.model.Veiculo;
import br.com.frota.util.Paginas;
import br.com.frota.util.TipoCombustivel;
import br.com.frota.util.TipoVeiculo;

@ManagedBean
@ViewScoped
public class VeiculoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private VeiculoDAO veiculoDAO;

	private Veiculo veiculo = new Veiculo();
	private Integer veiculoId;
	private String veiculoPlaca;
	private Integer veiculoAno;
	private TipoVeiculo veiculoTipo;
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
		veiculos = veiculoDAO.listarVeiculosPorNome(veiculoPlaca, veiculoAno, veiculoTipo);
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public TipoVeiculo getVeiculoTipo() {
		return veiculoTipo;
	}

	public void setVeiculoTipo(TipoVeiculo veiculoTipo) {
		this.veiculoTipo = veiculoTipo;
	}

	public Integer getVeiculoAno() {
		return veiculoAno;
	}

	public void setVeiculoAno(Integer veiculoAno) {
		this.veiculoAno = veiculoAno;
	}

	public TipoVeiculo[] getTipoVeiculo() {
		return TipoVeiculo.values();
	}
	
	public TipoCombustivel[] getTipoCombustivel() {
		return TipoCombustivel.values();
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		return null;
	}
	
	public String novo() {
		return Paginas.CADASTROVEICULO;
	}
	
	public void gravar() {
		veiculoDAO.gravar(veiculo);
		veiculo = new Veiculo();
	}

	public void recuperarVeiculoPorId() {
		veiculo = veiculoDAO.recuperarVeiculoPorId(veiculoId);
	}
	
	public void remover(Veiculo veiculo) {
		veiculoDAO.remover(veiculo);
	}
	
	public void remover() {
		veiculoDAO.remover(veiculo);
		veiculo = null;
	}

	public String editar(Veiculo veiculo) {
		this.veiculo = veiculo;
		veiculoId = veiculo.getId();
		return "veiculo?veiculoId="+veiculoId;
	}

}
