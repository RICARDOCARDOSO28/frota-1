package br.com.frota.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Setor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 2)
	@Column(name = "nome", nullable = false)
	private String nome;

	@Size(min = 4)
	@Column(name = "fone", length = 11)
	private String fone;

	@Column(name = "email")
	private String email;

	@OneToMany(mappedBy = "setor", cascade = CascadeType.ALL)
	private List<Usuario> usuarios = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void adicionarUsuarios(Usuario usuario) {
		getUsuarios().add(usuario);
	}

	public void removerUsuarios(Usuario usuario) {
		getUsuarios().remove(usuario);
	}

	public void adicionarUsuario(Usuario usuario) {
		usuario.setSetor(this);
		getUsuarios().add(usuario);
	}

	public void removerUsuario(Usuario usuario) {
		usuario.setSetor(null);
		getUsuarios().remove(usuario);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " [");
		builder.append("\n\tid: " + getId());
		builder.append("\n\tnome: " + getNome());
		builder.append("\n\tfone: " + getFone());
		builder.append("\n\temail: " + getEmail());
		builder.append("\n ]");
		return builder.toString();
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
		Setor other = (Setor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
