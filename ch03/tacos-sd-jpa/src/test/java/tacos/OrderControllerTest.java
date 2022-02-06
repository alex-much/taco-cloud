package tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.web.OrderController;

@WebMvcTest(controllers = {OrderController.class})
class OrderControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	IngredientRepository ingredMockRepo;
	
	@MockBean
	OrderRepository orderMockRepo;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShowOrderForm() throws Exception {
		mockMvc.perform(get("/orders/current"))
			.andDo(print())
			.andExpect(view().name("orderForm"))
			// more accurate to be sure that the OrderForm is displayed
			.andExpect(content().string(containsString("<h3>Deliver my taco masterpieces to...</h3>")));
	}
	
	@Test
	public void testSubmitOrderFormWithFieldEmpty() throws Exception {
		mockMvc.perform(post("/orders"))
			.andDo(print())
			// We stay on Order Form since validation errors
			.andExpect(view().name("orderForm"))
			// Thus model has errors
			.andExpect(model().hasErrors())
			// We receive an HTTP Code = 200 since 
			// we get back the form instead of a redirect
			.andExpect(status().isOk());
	}

}