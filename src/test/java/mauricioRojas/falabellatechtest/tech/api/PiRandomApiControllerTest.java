package mauricioRojas.falabellatechtest.tech.api;


import org.junit.Test;
import org.junit.runner.RunWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import mauricioRojas.falabellatechtest.tech.service.PiRandomService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PiRandomApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean(name = "PiRandomService")
	private PiRandomService piRandomService;

	@Test
	public void givenCorrectQueryParams_whenGetPiRandom10_thenStatus200() throws Exception {

		//test for a very low int number 10
		mvc.perform(get("/pi/")
		.queryParam("random_limit", "10") 
		.header("Authorization", "Basic ZmFsYWJlbGxhOnBhc3N3b3JkLjIwMjA")
		).andExpect(status().isOk());
	}

	@Test
	public void givenCorrectQueryParams_whenGetPiRandom1000_thenStatus200() throws Exception {

		//test for a medium number int number 1000
		mvc.perform(get("/pi/")
		.queryParam("random_limit", "1000") 
		.header("Authorization", "Basic ZmFsYWJlbGxhOnBhc3N3b3JkLjIwMjA")
		).andExpect(status().isOk());
	}

	@Test
	public void givenCorrectQueryParams_whenGetPiRandom100000_thenStatus200() throws Exception {

		//test for a larger number int number 100000
		mvc.perform(get("/pi/")
		.queryParam("random_limit", "100000") 
		.header("Authorization", "Basic ZmFsYWJlbGxhOnBhc3N3b3JkLjIwMjA")
		).andExpect(status().isOk());
	}

	@Test
	public void givenInvalidQueryParams_whenGetPiRandom_thenStatus400() throws Exception {
			//test for invalid input, containing chars and not a valid int
			mvc.perform(get("/pi/")
			.queryParam("random_limit", "10test")  
			.header("Authorization" , "Basic ZmFsYWJlbGxhOnBhc3N3b3JkLjIwMjA")
			).andExpect(status().isBadRequest());
	}

	@Test
	public void givenInvalidAuthHeader_whenGetPiRandom_thenStatus401() throws Exception {

			//test for invalid auth Header, incorrect password / user
			mvc.perform(get("/pi/")
			.queryParam("random_limit", "1000") 
			.header("Authorization" , "Basic wrongkey")
			).andExpect(status().isUnauthorized());
	}

}
