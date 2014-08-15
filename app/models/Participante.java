package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import models.exceptions.PessoaInvalidaException;

import org.hibernate.validator.constraints.Email;

import play.data.validation.Constraints.MaxLength;

@Entity
public class Participante {

	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@MaxLength(value = 70)
	private String nome;

	@Email
	@NotNull
	@MaxLength(value = 70)
	private String email;

	@NotNull
	@MaxLength(value = 30)
	private String senha;

	public Participante() {
	}

	public Participante(String nome, String email, String senha)
			throws PessoaInvalidaException {
		isSetNome(nome);
		isSetEmail(email);
		isSetSenha(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws PessoaInvalidaException {
		isSetNome(nome);
	}

	private void isSetNome(String nome) throws PessoaInvalidaException {
		isNull(nome, "Nome");
		if (nome.length() > 70)
			throw new PessoaInvalidaException("Nome longo");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws PessoaInvalidaException {
		isSetEmail(email);
	}

	private void isSetEmail(String email) throws PessoaInvalidaException {
		isNull(email, "Email");
		if (!email.matches(EMAIL_PATTERN))
			throw new PessoaInvalidaException("Email inválido");
		if (email.length() > 70)
			throw new PessoaInvalidaException("Email longo");
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws PessoaInvalidaException {
		isSetSenha(senha);
	}

	private void isSetSenha(String senha) throws PessoaInvalidaException {
		isNull(senha, "Senha");
		if (senha.length() < 6)
			throw new PessoaInvalidaException(
					"Senha nao pode ter menos que 6 caracteres");
		if (senha.length() > 30)
			throw new PessoaInvalidaException(
					"Senha nao pode ter mais que 30 caracteres");
		this.senha = senha;
	}

	private void isNull(Object obj, String nome) throws PessoaInvalidaException {
		if (obj == null) {
			throw new PessoaInvalidaException(nome + " nao pode ser nulo");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Nome = " + nome + ", Email = " + email;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Participante) {
			Participante p = (Participante) obj;
			return this.getEmail().equals(p.getEmail())
					&& this.getNome().equals(p.getNome());

		}
		return false;
	}
}