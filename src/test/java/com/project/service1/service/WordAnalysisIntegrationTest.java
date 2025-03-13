package com.project.service1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.service1.model.AnalysisResult;
import com.project.service1.entity.WordAnalysis;
import com.project.service1.repository.WordAnalysisRepository;
import com.project.service1.service.WordAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@SpringBootTest(classes = WordAnalysisIntegrationTest.class)
@AutoConfigureMockMvc
@AutoConfigureWebClient(registerRestTemplate = true)
public class WordAnalysisIntegrationTest {

    @MockitoBean
    private WordAnalysisService wordAnalysisService;

    @Autowired
    private RestTemplate restTemplate;

    @MockitoBean
    private WordAnalysisRepository repository;

    private MockRestServiceServer mockServer;

    private final String service2Url = "http://localhost:8081/analyze";

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testPalindromes() throws Exception {
        String input = "hello world";
        AnalysisResult service2Response = new AnalysisResult();
        service2Response.setId(UUID.randomUUID().toString());
        service2Response.setWordCount(2);
        service2Response.setPalindromeWords(Collections.emptyList());
        service2Response.setContainsPalindrome(false);

        mockServer.expect(requestTo(service2Url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(service2Response), MediaType.APPLICATION_JSON));

        when(repository.findAllPalindromeWords()).thenReturn(Collections.emptyList());

        WordAnalysis result = wordAnalysisService.processText(input);

        assertNull(result);
        verify(repository, never()).save(any(WordAnalysis.class));
    }

    @Test
    void testDuplicatePalindromes() throws Exception {
        String input = "madam racecar hello";
        AnalysisResult service2Response = new AnalysisResult();
        service2Response.setId(UUID.randomUUID().toString());
        service2Response.setWordCount(3);
        service2Response.setPalindromeWords(Arrays.asList("madam", "racecar"));
        service2Response.setContainsPalindrome(true);

        mockServer.expect(requestTo(service2Url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(service2Response), MediaType.APPLICATION_JSON));

        when(repository.findAllPalindromeWords()).thenReturn(Collections.singletonList("madam,racecar"));

        WordAnalysis result = wordAnalysisService.processText(input);

        assertNull(result);
        verify(repository, never()).save(any(WordAnalysis.class));
    }

    @Test
    void testServerError() throws Exception {
        String input = "madam racecar hello";

        mockServer.expect(requestTo(service2Url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        WordAnalysis result = wordAnalysisService.processText(input);

        assertNull(result);
        verify(repository, never()).save(any(WordAnalysis.class));
    }

}