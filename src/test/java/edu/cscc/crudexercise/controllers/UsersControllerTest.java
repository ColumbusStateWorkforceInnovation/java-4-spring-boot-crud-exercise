package edu.cscc.crudexercise.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cscc.crudexercise.models.User;
import edu.cscc.crudexercise.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UsersControllerTest {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        usersRepository.deleteAll();
    }

    @Test
    public void postCreatesANewUser() throws Exception {
        User user = new User("Harry Dresden", "harry@chicago.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));

        User foundUser = usersRepository.findAll().get(0);
        assertEquals(foundUser.getName(), user.getName());
        assertEquals(foundUser.getEmail(), user.getEmail());
    }

    @Test
    public void showRetrievesAUserById() throws Exception {
        User user = new User("Harry Dresden", "harry@chicago.com");
        user = usersRepository.create(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }
}