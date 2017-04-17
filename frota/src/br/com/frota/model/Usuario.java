package br.com.frota.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.frota.util.TipoSexo;
import br.com.frota.util.TipoUsuario;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", length = 80, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 11)
	private String cpf;

	@Column(name = "sexo", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoSexo sexo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "setor_fk", nullable = false)
	private Setor setor;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Abastecimento> abastecimentos = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Agenda> agendas = new ArrayList<>();

	@OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL)
	private List<ControleCirculacao> controles = new ArrayList<>();

	@Column(name = "fone", length = 11)
	private String fone;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "senha", length = 32)
	private String senha;

	@Column(name = "cnh_id", length = 11)
	private String cnhId;

	@Column(name = "cnh_validade")
	@Temporal(TemporalType.DATE)
	private Calendar cnhValidade = Calendar.getInstance();

	@Column(name = "tipo_usuario", nullable = false)
	private TipoUsuario tipoUsuario;

	@Column(name = "descricao")
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Abastecimento> getAbastecimentos() {
		return abastecimentos;
	}

	public void setAbastecimentos(List<Abastecimento> abastecimentos) {
		this.abastecimentos = abastecimentos;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public List<ControleCirculacao> getControles() {
		return controles;
	}

	public void setControles(List<ControleCirculacao> controles) {
		this.controles = controles;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCnhId() {
		return cnhId;
	}

	public void setCnhId(String cnhId) {
		this.cnhId = cnhId;
	}

	public Calendar getCnhValidade() {
		return cnhValidade;
	}

	public void setCnhValidade(Calendar cnhValidade) {
		this.cnhValidade = cnhValidade;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void adicionarAbastecimento(Abastecimento a) {
		a.setUsuario(this);
		getAbastecimentos().add(a);
	}

	public void removerAbastecimento(Abastecimento a) {
		a.setUsuario(null);
		getAbastecimentos().remove(a);
	}

	public void adicionarAgenda(Agenda a) {
		a.setUsuario(this);
		getAgendas().add(a);
	}

	public void removerAgenda(Agenda a) {
		a.setUsuario(null);
		getAgendas().remove(a);
	}

	public void adicionarControle(ControleCirculacao c) {
		c.setMotorista(this);
		getControles().add(c);
	}

	public void removerControle(ControleCirculacao c) {
		c.setMotorista(null);
		getControles().remove(c);
	}

	@Override
	public String toString() {
		return "Usuario[id:" + getId() + ", nome:" + getNome() + ", cpf:" + getCpf() + ", sexo:" + getSexo() + "]\n"
				+ getSetor().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
