package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.controllers.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Indiquem que aquest test només carrega la capa web (controladors)
@WebMvcTest(HealthController.class)
public class HealthControllerTest {

    // Injectem MockMvc, que ens permet simular peticions HTTP
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOkStatus() throws Exception {
        // Simulem una petició GET a /health
        mockMvc.perform(get("/health"))
                // Verifiquem que el codi de resposta és 200 OK
                .andExpect(status().isOk())
                // Comprovem que la resposta JSON conté "status": "OK"
                .andExpect(jsonPath("$.status").value("OK"));
    }

}
