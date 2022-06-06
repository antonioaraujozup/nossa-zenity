package br.com.zup.edu.nossozenity.zupper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class RemoverCertificadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ZupperRepository zupperRepository;

    @Autowired
    private CertificadoRepository certificadoRepository;

    @BeforeEach
    void setUp() {
        this.certificadoRepository.deleteAll();
        this.zupperRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve remover um certificado")
    void deveRemoverUmCertificado() throws Exception {

        // Cenário
        Zupper zupper = new Zupper("Antonio", "Desenvolvedor", LocalDate.now(), "antonio@email.com.br");
        Certificado certificado = new Certificado("Java", "Zup", "http://localhost:8080", zupper, TipoCertificado.CURSO);
        this.zupperRepository.save(zupper);
        this.certificadoRepository.save(certificado);

        MockHttpServletRequestBuilder request = delete("/certificados/{id}", certificado.getId());

        // Ação e Corretude
        mockMvc.perform(request)
                .andExpect(
                        status().isNoContent()
                );

        // Asserts
        assertFalse(this.certificadoRepository.existsById(certificado.getId()), "Não deve existir um registro para esse id");

    }

    @Test
    @DisplayName("Não deve remover um certificado não cadastrado")
    void naoDeveRemoverUmCertificadoNaoCadastrado() throws Exception {

        // Cenário
        MockHttpServletRequestBuilder request = delete("/certificados/{id}", Integer.MAX_VALUE);

        // Ação e Corretude
        Exception resolvedException = mockMvc.perform(request)
                .andExpect(
                        status().isNotFound()
                )
                .andReturn()
                .getResolvedException();

        // Asserts
        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        assertEquals("Certificado não cadastrado.", ((ResponseStatusException) resolvedException).getReason());

    }

}