package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.AddressBookController;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class HTTPRequestTest {

    @Autowired
    private MockMvc mockMvc;

    public static String asJSONString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    public void testCreatingBook() throws Exception {
        this.mockMvc.perform(get("/addressbook")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAddingBuddy() throws Exception {
        this.mockMvc.perform(post("/addToAddressBook/1")
                .content(asJSONString(new BuddyInfo("test", "test number", "test address")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveBuddy() throws Exception {
        Map<String, Long> removeInfo = new HashMap<>();
        removeInfo.put("buddyId", 1L);
        removeInfo.put("addressBookId", 1L);
        this.mockMvc.perform(post("/removeBuddy")
                        .content(asJSONString(removeInfo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

}

