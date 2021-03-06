package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import models.exceptions.EventoInvalidoException;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Evento {

	@Id
	@GeneratedValue
	private long id;

	@Required
	@MaxLength(value = 40)
	private String titulo;

	@Required
	@MaxLength(value = 450)
	@Column(name = "CONTENT", length = 450)
	private String descricao;

	@Temporal(value = TemporalType.DATE)
	@Required
	private Date data;

	@OneToMany
	private List<Participante> participantes = new ArrayList<Participante>();

	@ElementCollection
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private List<Tema> temas = new ArrayList<Tema>();

	public Evento() {

	}

	public Evento(String titulo, String descricao, Date data, List<Tema> temas)
			throws EventoInvalidoException {
		isSetTitulo(titulo);
		isSetDescricao(descricao);
		isSetData(data);
		isSetTemas(temas);
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public long getId() {
		return id;
	}

	public Integer getTotalDeParticipantes() {
		return participantes.size();
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTitulo(String titulo) throws EventoInvalidoException {
		isSetTitulo(titulo);
	}

	private void isSetTitulo(String titulo) throws EventoInvalidoException {
		isNull(titulo);
		if (titulo.length() > 40) {
			throw new EventoInvalidoException("Título longo");
		}
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) throws EventoInvalidoException {
		isSetDescricao(descricao);
	}

	private void isSetDescricao(String descricao)
			throws EventoInvalidoException {
		isNull(descricao);
		if (descricao.length() > 450) {
			throw new EventoInvalidoException("Descrição longa");
		}
		this.descricao = descricao;
	}

	public void setData(Date data) throws EventoInvalidoException {
		isSetData(data);
	}

	private void isSetData(Date data) throws EventoInvalidoException {
		isNull(data);
		if (data.compareTo(new Date()) < 0) {
			throw new EventoInvalidoException("Data inválida");
		}
		this.data = data;
	}

	public void setTemas(List<Tema> temas) throws EventoInvalidoException {
		isSetTemas(temas);
	}

	private void isSetTemas(List<Tema> temas) throws EventoInvalidoException {
		isNull(temas);
		if (temas.size() == 0) {
			throw new EventoInvalidoException("Nenhum tema");
		}
		this.temas = temas;
	}

	private void isNull(Object obj) throws EventoInvalidoException {
		if (obj == null) {
			throw new EventoInvalidoException("Parametro nulo");
		}
	}
}
