package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.db.jpa.Transactional;

public class Login extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<Participante> loginForm = form(Participante.class).bindFromRequest();

	@Transactional
	public static Result show() {
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
		return ok(views.html.login.render(loginForm));
	}

	@Transactional
	public static Result logout() {
		session().clear();
		return show();
	}

	@Transactional
	public static Result authenticate() {
		
		Form<Participante> form = loginForm.bindFromRequest();

		String email = form.field("email").value();
		String senha = form.field("senha").value();

		if (!validate(email, senha)) {
			flash("fail", "Email ou Senha Inválidos");
			return badRequest(views.html.login.render(loginForm));
		} else {
			Participante user = (Participante) dao.findByAttributeName(
					"Participante", "email", email).get(0);
			session().clear();
			session("user", user.getNome());
			return redirect(routes.Application.index());
		}
	}

	private static boolean validate(String email, String senha) {
		List<Participante> u = dao.findByAttributeName("Participante", "email",
				email);
		if (u == null || u.isEmpty()) {
			return false;
		}
		if (!u.get(0).getSenha().equals(senha)) {
			return false;
		}
		return true;
	}
}