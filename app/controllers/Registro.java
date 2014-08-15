package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Registro extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<Participante> registroForm = form(Participante.class)
			.bindFromRequest();

	@Transactional
	public static Result show() {
		return ok(views.html.registro.render(registroForm, ""));
	}

	@Transactional
	public static Result registrar() {
		
		Form<Participante> form = registroForm.bindFromRequest();
		
		String nome = form.field("nome").value();
		String senha = form.field("senha").value();
		String email = form.field("email").value();
		
		Participante u;
		
		try {
			u = new Participante(nome, email, senha);
		} catch (Exception e) {
			flash("fail", e.getMessage());
			return ok(views.html.registro.render(registroForm, e.getMessage()));
		}
		
		if (validate(email)) {
			flash("fail", "Email já está em uso");
			return badRequest(views.html.registro.render(registroForm, ""));
		} else {
			dao.persist(u);
			dao.merge(u);
			dao.flush();
			return redirect(routes.Login.show());
		}
	}

	private static boolean validate(String email) {
		List<Participante> u = dao.findByAttributeName("Participante", "email",
				email);

		for (Participante participante : u) {
			if (participante.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

}
