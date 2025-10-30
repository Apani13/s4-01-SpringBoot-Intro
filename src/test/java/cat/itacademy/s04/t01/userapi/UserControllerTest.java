package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.controllers.UserController;
import cat.itacademy.s04.t01.userapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User cassendrola;
    private User anatoli;
    private User gerfizundo;

    @BeforeEach
    void setUp() {

        UserController.users.clear();

        cassendrola = new User(null, "Cassendrola Ancarte", "cassenArte@gmail.com");
        anatoli = new User(null, "Anatoli Casantrin", "toliCasa@gmail.com");
        gerfizundo = new User(null, "Gerfizundo Templado", "zundoLado@gmail.com");
    }


    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    void createUser_returnsUserWithId() throws Exception {

        String requestBody = objectMapper.writeValueAsString(cassendrola);

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cassendrola"))
                .andExpect(jsonPath("$.email").value("cassenArte@gmail.com"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    void getUserById_returnsCorrectUser() throws Exception {

        String postResponseJson = mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cassendrola))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User created = objectMapper.readValue(postResponseJson, User.class);
        UUID createdId = created.getId();

        mockMvc.perform(get("/users/{id}", createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId.toString()))
                .andExpect(jsonPath("$.name").value("Cassendrola"))
                .andExpect(jsonPath("$.email").value("cassenArte@gmail.com"));
    }


    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/users/{id}", randomId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User with id " + randomId + " not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anatoli))
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gerfizundo))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/users").param("name", "an"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Anatoli Casantrin"))
                .andExpect(jsonPath("$[0].email").value("toliCasa@gmail.com"));
    }

}
