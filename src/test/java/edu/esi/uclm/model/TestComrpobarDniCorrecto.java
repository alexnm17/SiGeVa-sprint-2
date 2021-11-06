package edu.esi.uclm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uclm.esi.exceptions.SiGeVaException;

class TestComrpobarDniCorrecto {

	@Test
	void test() {
		Usuario user = new Usuario("1234567B","","", null, null, null, null);
		try {
			user.comprobarDni();
		} catch (SiGeVaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
