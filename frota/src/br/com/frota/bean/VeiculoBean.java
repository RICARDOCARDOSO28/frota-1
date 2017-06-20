package br.com.frota.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
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

	private Veiculo veiculo = new Veiculo();

	private Integer veiculoId;

	// Atributos para Consulta
	private String veiculoPlaca;
	private Integer veiculoAno;
	private TipoVeiculo veiculoTipo;

	// Listas para Consulta
	private List<Veiculo> veiculos;
	private List<Veiculo> veiculosConsulta = new ArrayList<>();

	// Dao
	private VeiculoDAO veiculoDAO;

	public VeiculoBean() {
		veiculoDAO = new VeiculoDAO();
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
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
		return veiculos;
	}

	public List<Veiculo> getVeiculosConsulta() {
		return veiculosConsulta;
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

	@PostConstruct
	public void createVeiculos() {
		if (this.veiculos == null) {
			veiculosConsulta.clear();
			veiculos = veiculoDAO.listarVeiculosPorNome(veiculoPlaca, veiculoAno, veiculoTipo);
			veiculosConsulta.addAll(veiculos);
		}
		createVeiculosConsulta();
	}

	public void createVeiculosConsulta() {
		Stream<Veiculo> consulta = veiculosConsulta.stream();

		if (veiculoPlaca != null)
			consulta = consulta.filter(v -> v.getPlaca().toLowerCase().contains(veiculoPlaca.toLowerCase()));

		if (veiculoAno != null)
			consulta = consulta.filter(v -> (v.getAno().equals(veiculoAno)));

		if (veiculoTipo != null)
			consulta = consulta.filter(u -> (u.getTipoVeiculo().equals(veiculoTipo)));

		veiculos = consulta.collect(Collectors.toList());
	}

	public Integer getTotalVeiculos() {
		return veiculos.size();
	}

	public Integer getTotalVeiculosConsulta() {
		return veiculosConsulta.size();
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		createVeiculos();
		return null;
	}

	public String limpar() {
		veiculoPlaca = "";
		veiculoAno = null;
		veiculoTipo = null;
		createVeiculos();
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
		this.veiculo = veiculo;
		remover();
	}

	public void remover() {
		veiculoDAO.remover(veiculo);
		veiculos = null;
		createVeiculos();
		veiculo = null;
	}

	public String editar() {
		veiculoId = veiculo.getId();
		return "veiculo?veiculoId=" + veiculoId;
	}

	public String editar(Veiculo veiculo) {
		this.veiculo = veiculo;
		return editar();
	}

}
