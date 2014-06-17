package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Evento;
import models.EventoComparator;
import models.Participante;
import models.Tema;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoController extends Controller {

	final static Form<Evento> eventoForm = form(Evento.class);
	final static Form<Participante> participanteForm = form(Participante.class);

	@Transactional
	public static Result eventosPorTema(int id) throws PessoaInvalidaException, EventoInvalidoException{
	
		List<Evento> todosEventos = Application.dao.findAllByClassName("Evento");
		
		List<Evento> eventosRequeridos = new ArrayList<>();
		
		for (Evento ev : todosEventos) {
			if (ev.getTemas().contains(Tema.values()[(int) id])){
				eventosRequeridos.add(ev);
			}
		}

		Collections.sort(eventosRequeridos, new EventoComparator());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		try {
			json = mapper.writeValueAsString(eventosRequeridos);
		} catch (Exception _) {
			return badRequest();
		}
		
		return ok(json);
	}
	
	@Transactional
	public static Result novo() throws PessoaInvalidaException, EventoInvalidoException{
		Form<Evento> eventoFormRequest = eventoForm.bindFromRequest();

		if (eventoForm.hasErrors()) {
			return badRequest();
		} else {
			Evento novoEvento = eventoFormRequest.get();
			Application.dao.persist(novoEvento);
			Application.dao.merge(novoEvento);
			Application.dao.flush();
			return redirect(controllers.routes.Application.index());
		}
	}
	
	@Transactional
	public static Result participar(long id) throws PessoaInvalidaException, EventoInvalidoException{
		Form<Participante> participanteFormRequest = participanteForm.bindFromRequest();
		
		if (participanteForm.hasErrors()) {
			return badRequest();
		} else {
			Evento evento = Application.dao.findByEntityId(Evento.class, id);
			Participante novoParticipante = participanteFormRequest.get();
			novoParticipante.setEvento(evento);
			
			Application.dao.persist(novoParticipante);
			Application.dao.merge(novoParticipante);
			Application.dao.flush();
			return redirect(controllers.routes.Application.index());
		}
	}
}
