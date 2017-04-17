package br.com.frota.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.AbastecimentoDAO;
import br.com.frota.model.Abastecimento;
import br.com.frota.util.Paginas;

@ManagedBean
@ViewScoped
public class AbastecimentoBean {

	private AbastecimentoDAO abastecimentoDAO;

	private Abastecimento abastecimento = new Abastecimento();
	private Integer abastecimentoId;
	private String abastecimentoNome;
	private List<Abastecimento> abastecimentos;

	private Date dataInicial = null;
	private Date dataFinal = Calendar.getInstance().getTime();

	private int usuarioId;
	private int veiculoId;

	public AbastecimentoBean() {
		abastecimentoDAO = new AbastecimentoDAO();
	}

	public Abastecimento getAbastecimento() {
		return abastecimento;
	}

	public Integer getAbastecimentoId() {
		return abastecimentoId;
	}

	public void setAbastecimentoId(Integer abastecimentoId) {
		this.abastecimentoId = abastecimentoId;
	}

	public List<Abastecimento> getAbastecimentos() {
		abastecimentos = abastecimentoDAO.listarAbastecimentosPorNome(abastecimentoNome, dataInicial, dataFinal);
		return abastecimentos;
	}

	public void setAbastecimentos(List<Abastecimento> abastecimentos) {
		this.abastecimentos = abastecimentos;
	}

	public int getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(int veiculoId) {
		this.veiculoId = veiculoId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getAbastecimentoNome() {
		return abastecimentoNome;
	}

	public void setAbastecimentoNome(String abastecimentoNome) {
		this.abastecimentoNome = abastecimentoNome;
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

	/**
	 * Finalizados GET e SET - Iniciando MÉTODOS
	 */

	public String listar() {
		return null;
	}

	public String gravar() {
		abastecimentoDAO.gravar(abastecimento, usuarioId, veiculoId);
		abastecimento = new Abastecimento();
		return Paginas.CADASTROABASTECIMENTO;
	}

	public Abastecimento recuperarAbastecimentoPorId() {
		abastecimento = abastecimentoDAO.recuperarAbastecimentoPorId(abastecimentoId);
		return abastecimento;
	}

	public void remover(Abastecimento abastecimento) {
		abastecimentoDAO.remover(abastecimento);
	}

	public void remover() {
		abastecimentoDAO.remover(abastecimento);
		abastecimento = null;
	}

	public String editar(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
		abastecimentoId = abastecimento.getId();
		veiculoId = abastecimento.getVeiculo().getId();
		usuarioId = abastecimento.getUsuario().getId();
		System.out.println("abastecimentoId= "+abastecimentoId+", aeiculoId= "+veiculoId+", usuarioId="+usuarioId);
		return "abastecimento?abastecimentoId=" + abastecimentoId;
	}

	public String novo() {
		return Paginas.CADASTROABASTECIMENTO;
	}

}
