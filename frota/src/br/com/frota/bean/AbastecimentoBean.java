package br.com.frota.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.frota.dao.AbastecimentoDAO;
import br.com.frota.model.Abastecimento;

@ManagedBean
@ViewScoped
public class AbastecimentoBean {

	private static final String CADASTROABASTECIMENTO = "abastecimento?faces-redirect=true";
	private static final String CONSULTAABASTECIMENTO = "consultaabastecimento?faces-redirect=true";
	private static final String ABASTECIMENTO = "abastecimento?faces-redirect=true";



	private AbastecimentoDAO abastecimentoDAO;

	private Abastecimento abastecimento = new Abastecimento();
	private Integer abastecimentoId;
	private List<Abastecimento> abastecimentos;
	private String abastecimentoNome;

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

	public String gravar() {
		abastecimentoDAO.gravar(abastecimento, usuarioId, veiculoId);
		abastecimento = new Abastecimento();
		return "abastecimento?faces-redirect=true";

	}

	public Abastecimento recuperarAbastecimentoPorId() {
		return abastecimentoDAO.recuperarAbastecimentoPorId(abastecimentoId);
	}

	public String listarPorNome() {
		return null;
	}

	public String novoVeiculo() {
		return "veiculo?faces-redirect=true";
	}

	public void remover(Abastecimento abastecimento) {
		abastecimentoDAO.remover(abastecimento);
	}

	public String editar(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
		return "abastecimento?faces-redirect=true";
	}

	public String novoAbastecimento() {
		return CADASTROABASTECIMENTO;
	}
}
