package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
		setNome(nome);
		setEmail(email);
		setSenha(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws PessoaInvalidaException {
		if (isNull(nome))
			throw new PessoaInvalidaException("Nome nao pode ser nulo");
		if (nome.length() > 70)
			throw new PessoaInvalidaException("Nome longo");
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws PessoaInvalidaException {
		if (isNull(email))
			throw new PessoaInvalidaException("Email nao pode ser nulo");
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
		if (isNull(senha))
			throw new PessoaInvalidaException("Senha nao pode ser nula");
		if (senha.length() < 6)
			throw new PessoaInvalidaException("Senha nao pode ter menos que 6 caracteres");
		if (senha.length() > 30)
			throw new PessoaInvalidaException("Senha nao pode ter mais que 30 caracteres");
		this.senha = senha;
	}

	private boolean isNull(Object obj) {
		return obj == null;
		
	}
}