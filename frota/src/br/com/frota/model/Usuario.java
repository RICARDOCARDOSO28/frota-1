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

import org.hibernate.annotations.ColumnDefault;

import br.com.frota.util.TipoSexo;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", length = 80, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 11, unique = true)
	private String cpf;

	@Column(name = "sexo", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoSexo sexo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "setor_fk", nullable = false)
	private Setor setor;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Abastecimento> abastecimentos = new ArrayList<>();

	// @OneToMany(mappedBy = "usuario", cascade=CascadeType.ALL)
	// private List<Agenda> agendas = new ArrayList<>();

	// @OneToMany(mappedBy = "motorista", cascade=CascadeType.ALL)
	// private List<ControleCirculacao> controles = new ArrayList<>();

	@Column(name = "fone", length = 11)
	private String fone;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "senha", length=32)
	private String senha;

	@Column(name = "cnh_id", length = 11)
	private String cnhId;

	@Column(name = "cnh_validade")
	@Temporal(TemporalType.DATE)
	private Calendar cnhValidade = Calendar.getInstance();

	// 0 SuperUser(existe somente 1); 1 Admins; 2 Users
	// @Column(name = "admin_status", nullable = false, length = 1)
	// private Integer adminStatus;

	@ColumnDefault("2")
	@Column(name = "admin_status", nullable = false, length = 1)
	private Integer tipoUsuario;

	@Column(name = "descricao")
	private String descricao;

	public Integer getId() {
		return id;
	}

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

	public List<Abastecimento> getAbastecimentos() {
		return abastecimentos;
	}

	public void setAbastecimentos(List<Abastecimento> abastecimentos) {
		this.abastecimentos = abastecimentos;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Usuario[id:" + getId() + ", nome:" + getNome() + ", cpf:" + getCpf() + ", sexo:" + getSexo()
				+ ", setor:" + getSetor() + ", fone:" + getFone() + ", email:" + getEmail() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

}
