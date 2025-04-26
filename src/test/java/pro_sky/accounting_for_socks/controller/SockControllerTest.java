package pro_sky.accounting_for_socks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro_sky.accounting_for_socks.model.dto.SockRequest;
import pro_sky.accounting_for_socks.service.SockService;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SockController.class)
class SockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SockService sockService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void income_ShouldReturnOk() throws Exception {
        SockRequest request = new SockRequest();
        request.setColor("red");
        request.setCottonPart(80);
        request.setQuantity(10);

        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(sockService).income(eq("red"), eq(80), eq(10));
    }

    @Test
    void outcome_ShouldReturnOk() throws Exception {
        SockRequest request = new SockRequest();
        request.setColor("blue");
        request.setCottonPart(60);
        request.setQuantity(5);

        mockMvc.perform(post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(sockService).outcome(eq("blue"), eq(60), eq(5));
    }

    @Test
    void getSocks_ShouldReturnQuantity() throws Exception {
        Mockito.when(sockService.getSocks("white", "moreThan", 70)).thenReturn(15);

        mockMvc.perform(get("/api/socks")
                        .param("color", "white")
                        .param("operation", "moreThan")
                        .param("cottonPart", "70"))
                .andExpect(status().isOk())
                .andExpect(content().string("15"));
    }

    @Test
    void getSocks_NoResult_ShouldReturnZero() throws Exception {
        Mockito.when(sockService.getSocks("white", "moreThan", 70)).thenReturn(null);

        mockMvc.perform(get("/api/socks")
                        .param("color", "white")
                        .param("operation", "moreThan")
                        .param("cottonPart", "70"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}
