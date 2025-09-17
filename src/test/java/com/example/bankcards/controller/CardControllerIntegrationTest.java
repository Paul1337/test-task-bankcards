package com.example.bankcards.controller;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CardControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    User testUser = null;

    @BeforeEach
    void setUp() {
        testUser = new User("username", "name", "password123");
        userRepository.save(testUser);
    }

    @Test
    void createCard_noAuth_unauthorized() throws Exception {
        String json = """
            {
                "number": "5555444433332225",
                "ownerId": "23b3f0c0-8323-440c-b4d9-0d9cf282c802"
            }
            """;
        mockMvc.perform(post("/cards/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "username", authorities = "ROLE_USER")
    @Test
    void createCard_user_forbidden() throws Exception {
        String json = """
            {
                "number": "5555444433332225",
                "ownerId": "23b3f0c0-8323-440c-b4d9-0d9cf282c802"
            }
            """;
        mockMvc.perform(post("/cards/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "username", roles = "ADMIN")
    @Test
    void createCard_user_success() throws Exception {
        String json = """
            {
                "number": "5555444433332225",
                "ownerId": "%s"
            }
            """.formatted(testUser.getId().toString());
        mockMvc.perform(post("/cards/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Optional<Card> cardOpt = cardRepository.findById("5555444433332225");
        assertTrue(cardOpt.isPresent(), "Card should be saved in database");

        Card card = cardOpt.get();
        assertEquals(testUser.getId(), card.getOwner().getId(), "The owner of saved card should be the same as in the request");
    }
}
