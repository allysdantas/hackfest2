package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import models.Evento;
import models.Participante;
import models.Tema;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	
	private static boolean criouEventosFake = false;
	private static GenericDAO dao = new GenericDAOImpl();
	private static final int DATA_SETE = 7, DATA_TRES = 3, DATA_UM = 1,
			DATA_DOZE = 12, DATA_DEZESSETE = 17, DATA_CINCO = 5,
			DATA_VINTE_UM = 21, DATA_QUINZE = 15, DATA_OITO = 8;

	@Transactional
    public static Result index(){
		if (!criouEventosFake){
			List<Evento> eventos = criarEventosFakes();
			criarParticipacoesFake();

			criouEventosFake = true;
		}
		if (session().get("user") == null) {
			return redirect(routes.Login.show());
		}
        return ok(views.html.index.render());
    }

	public static GenericDAO getDao(){
		return dao;
	}

	private static List<Evento> criarEventosFakes() {
		try {
			List<Evento> eventos = new ArrayList<Evento>();
			Evento evento;
			Calendar calendar;
	
			List<Tema> temas = new ArrayList<Tema>();
			
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_SETE);
			
			evento = new Evento("Python na mente e coração", "Neste evento iremos debater e propor soluções para novas releases.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
			
			temas = new ArrayList<Tema>();
			temas.add(Tema.ARDUINO);
			temas.add(Tema.ELETRONICA);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento("Luta de robôs", "Traga seu robô feito em arduino e traga para competir com outros.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, DATA_UM);

			evento = new Evento("IV Olímpiadas de programação da UFCG", "Traga sua equipe e venha competir nessa maratona de programação.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DOZE);

			evento = new Evento("II Encontro para programadores de Python", "O encontro contará com a participação de um de seus fundadores, inúmeras palestras e maratonas. Não percam!!", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento("III Semana da Computação Verde", "Exiba sua proposta para uma computação mais verde e concorra a diversos prêmios", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.WEB);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DEZESSETE);

			evento = new Evento("Web em foco", "Este evento contará com a participação de um dos fundadores da Web, e juntos iremos compartilhar diversas dicas e boas práticas nessa vasta área.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_CINCO);

			evento = new Evento("Minicurso Arduino", "Evento destinado a alunos de LOAC, caso sobre vagas iremos disponibilizar em breve", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_VINTE_UM);

			evento = new Evento("Curto circuito", "Evento sobre circuitos eletrônicos, venha dar curto em seus circuitos também!!", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_QUINZE);

			evento = new Evento("VI Encontro de Docentes de CC", "Evento para debatermos propostas e soluções para os problemas enfrentados pelos alunos de CC.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_OITO);

			evento = new Evento("Café com Java", "Curso destinado apenas a alunos cursando a disciplina LP2.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
			
			return eventos;
		} catch (EventoInvalidoException e) {
			return null;
		}
	}
	
	private static void criarParticipacoesFake() {
		try {
			criarParticipacao(new Participante("Alberto Leça", "123456", "alberto_leca@mail.com"));
			criarParticipacao(new Participante("Alberto Leça", "123456", "alberto_leca@mail.com"));
			criarParticipacao(new Participante("Alberto Leça", "123456", "alberto_leca@mail.com"));
			criarParticipacao(new Participante("Belmifer Linares", "123456", "belmifer_linares@mail.com"));
			criarParticipacao(new Participante("Belmifer Linares", "123456", "belmifer_linares@mail.com"));
			criarParticipacao(new Participante("Célia Rúa", "123456", "celia_rua@mail.com"));
			criarParticipacao(new Participante("Deolindo Castello Branco", "123456", "deolindo_castello@mail.com"));
			criarParticipacao(new Participante("Doroteia Pasos", "123456", "doroteia_passos@mail.com"));
			criarParticipacao(new Participante("Eugénio Palhares", "123456", "eugenio_palhares@mail.com"));
			criarParticipacao(new Participante("Fausto Furtado", "123456", "fausto_furtado@mail.com"));
			criarParticipacao(new Participante("Filipa Leiria", "123456", "filipa_leiria@mail.com"));
			criarParticipacao(new Participante("Leonilde Figueiredo", "123456", "leonilde_figueiredo@mail.com"));
			criarParticipacao(new Participante("Pascoal Caldeira", "123456", "pascoal_caldeira@mail.com"));
			criarParticipacao(new Participante("Paula Lousado", "123456", "paula_lousado@mail.com"));
			criarParticipacao(new Participante("Quitério Galindo", "123456", "quiterio_galindo@mail.com"));
			criarParticipacao(new Participante("Rosa Varejão", "123456", "rosa_varejao@mail.com"));
			criarParticipacao(new Participante("Sonia Gabeira", "123456", "sonia_gabeira@mail.com"));
			criarParticipacao(new Participante("Érico Albuquerque", "123456", "erico_albuquerque@mail.com"));
			criarParticipacao(new Participante("Érico Albuquerque", "123456", "erico_albuquerque@mail.com"));
			criarParticipacao(new Participante("Tairine Reis", "123456", "tairine_reis@mail.com"));
		} catch (PessoaInvalidaException e) { }
	}
	
	@Transactional
	private static void criarEvento(Evento evento) {
		dao.persist(evento);
		dao.merge(evento);
		dao.flush();
	}
	
	@Transactional
	private static void criarParticipacao(Participante participante) {
		dao.persist(participante);
		dao.flush();
	}

}
