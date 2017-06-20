package br.com.frota.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.AbastecimentoDAO;
import br.com.frota.model.Abastecimento;
import br.com.frota.util.Paginas;
import br.com.frota.util.TipoVeiculo;

@ManagedBean
@ViewScoped
public class AbastecimentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Abastecimento abastecimento = new Abastecimento();

	private Integer abastecimentoId;

	// Atributos para Consulta
	private Integer usuarioId;
	private Integer veiculoId;

	private String abastecimentoNome;
	private TipoVeiculo veiculoTipo;
	private Date dataInicial = null;
	private Date dataFinal = Calendar.getInstance().getTime();

	// Listas para Consulta
	private List<Abastecimento> abastecimentos;
	private List<Abastecimento> abastecimentosConsulta = new ArrayList<>();

	// Dao
	private AbastecimentoDAO abastecimentoDAO;

	public AbastecimentoBean() {
		abastecimentoDAO = new AbastecimentoDAO();
	}

	public Abastecimento getAbastecimento() {
		return abastecimento;
	}

	public void setAbastecimento(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
	}

	public Integer getAbastecimentoId() {
		return abastecimentoId;
	}

	public void setAbastecimentoId(Integer abastecimentoId) {
		this.abastecimentoId = abastecimentoId;
	}

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getAbastecimentoNome() {
		return abastecimentoNome;
	}

	public void setAbastecimentoNome(String abastecimentoNome) {
		this.abastecimentoNome = abastecimentoNome;
	}

	public TipoVeiculo getVeiculoTipo() {
		return veiculoTipo;
	}

	public void setVeiculoTipo(TipoVeiculo veiculoTipo) {
		this.veiculoTipo = veiculoTipo;
	}

	public TipoVeiculo[] getTipoVeiculos() {
		return TipoVeiculo.values();
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Abastecimento> getAbastecimentos() {
		return abastecimentos;
	}

	public List<Abastecimento> getAbastecimentosConsulta() {
		return abastecimentosConsulta;
	}

	public Integer getTotalAbastecimentos() {
		return abastecimentos.size();
	}

	public Integer getTotalAbastecimentosConsulta() {
		return abastecimentosConsulta.size();
	}

	@PostConstruct
	public void createAbastecimentos() {
		if (this.abastecimentos == null) {
			abastecimentosConsulta.clear();
			abastecimentos = abastecimentoDAO.listarAbastecimentosPorNome(abastecimentoNome, dataInicial, dataFinal);
			abastecimentosConsulta.addAll(abastecimentos);
		}
		createAbastecimentosConsulta();
	}

	public void createAbastecimentosConsulta() {
		Stream<Abastecimento> consulta = abastecimentosConsulta.stream();

		if (abastecimentoNome != null)
			consulta = consulta
					.filter(a -> (a.getVeiculo().getPlaca().toLowerCase().contains(abastecimentoNome.toLowerCase())
							| a.getUsuario().getNome().toLowerCase().contains(abastecimentoNome.toLowerCase())));

		Calendar datanova = Calendar.getInstance();
		if (dataInicial != null) {
			datanova.setTime(dataInicial);
			datanova.add(Calendar.DATE, -1);
			Date time1 = datanova.getTime();
			consulta = consulta.filter(a -> (a.getDataAbastecimento().getTime().after(time1)));
		}

		if (dataFinal != null) {
			datanova.setTime(dataFinal);
			datanova.add(Calendar.DATE, 1);
			Date time = datanova.getTime();
			consulta = consulta.filter(a -> (a.getDataAbastecimento().getTime().before(time)));
		}

		if (veiculoTipo != null)
			consulta = consulta.filter(a -> (a.getVeiculo().getTipoVeiculo().equals(veiculoTipo)));

		abastecimentos = consulta.collect(Collectors.toList());
	}

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		createAbastecimentos();
		return null;
	}

	public String limpar() {
		abastecimentoNome = "";
		dataInicial = null;
		dataFinal = Calendar.getInstance().getTime();
		veiculoTipo = null;
		createAbastecimentos();
		return null;
	}

	public String novo() {
		return Paginas.CADASTROABASTECIMENTO;
	}

	public String gravar() {
		abastecimentoDAO.gravar(abastecimento, usuarioId, veiculoId);
		abastecimento = new Abastecimento();
		return null;
	}

	public void recuperarAbastecimentoPorId() {
		abastecimento = abastecimentoDAO.recuperarAbastecimentoPorId(abastecimentoId);
	}

	public void remover(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
		remover();
	}

	public void remover() {
		abastecimentoDAO.remover(abastecimento);
		abastecimentos = null;
		createAbastecimentos();
		abastecimento = null;
	}

	public String editar() {
		abastecimentoId = abastecimento.getId();
		return "abastecimento?abastecimentoId=" + abastecimentoId;
	}

	public String editar(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
		return editar();
	}

}
