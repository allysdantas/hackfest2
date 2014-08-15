import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.Participante;
import models.Tema;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;

import org.junit.Before;
import org.junit.Test;

public class ParticipanteTest {

	@Test
	public void deveOcorrerErroNaSenha() {
		
		try {
			new Participante("Allys", "allys@outlook.com.br", "");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha nao pode ter menos que 6 caracteres", e.getMessage());
		}
		
		try {
			new Participante("Allys", "allys@outlook.com.br", null);
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha nao pode ser nulo", e.getMessage());
		}
		
		try {
			new Participante("Allys", "allys@outlook.com.br", "1234567890123456789012345678901");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha nao pode ter mais que 30 caracteres", e.getMessage());
		}
		
		try {
			new Participante("Allys", "allys@outlook.com.br", "");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha nao pode ter menos que 6 caracteres", e.getMessage());
		}
	}
	
	@Test
	public void deveOcorrerErroNoEmail() {
		try {
			new Participante(
					"João José da Silva",
					"joao_jose_da_silva_maria_da_penha_do_ultimo_socorro_pereira_lima@mail.com",
					"123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email longo", e.getMessage());
		}
		
		try {
			new Participante("João José da Silva", null, "123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email nao pode ser nulo", e.getMessage());
		}
		try {
			new Participante("João José da Silva", null, "123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email nao pode ser nulo", e.getMessage());
		}
		try {
			new Participante("João José da Silva", "joao_jose_mail.com",
					"123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email inválido", e.getMessage());
		}
	}
	
	@Test
	public void deveLancarExcecaoNoNome() {
		try {
			new Participante(
					"João José da Silva Maria da Penha do Ultimo Socorro Pereira Lima Roberto",
					"joao_jose@mail.com", "123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Nome longo", e.getMessage());
		}
		
		try {
			new Participante(null, "joao_jose@mail.com", "123456");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Nome nao pode ser nulo", e.getMessage());
		}
	}
}
