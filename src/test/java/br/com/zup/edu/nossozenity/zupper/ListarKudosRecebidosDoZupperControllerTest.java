package br.com.zup.edu.nossozenity.zupper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.assertj.core.groups.Tuple;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class ListarKudosRecebidosDoZupperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ZupperRepository zupperRepository;

    @Autowired
    private KudoRepository kudoRepository;

    @BeforeEach
    void setUp() {
        this.kudoRepository.deleteAll();
        this.zupperRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve listar os kudos recebidos por um zupper")
    void deveListarOsKudosRecebidosPorUmZupper() throws Exception {

        // Cenário
        Zupper zupperReceptor = new Zupper("Antonio", "Desenvolvedor", LocalDate.now(), "antonio@zup.com.br");
        Zupper zupperEmissor = new Zupper("Maiana", "Desenvolvedor", LocalDate.now(), "maiana@zup.com.br");

        zupperRepository.saveAll(List.of(zupperReceptor, zupperEmissor));

        Kudo kudo1 = new Kudo(TipoKudo.AGRADECIMENTO, zupperEmissor, zupperReceptor);
        Kudo kudo2 = new Kudo(TipoKudo.EXCELENTE_MENTOR, zupperEmissor, zupperReceptor);
        Kudo kudo3 = new Kudo(TipoKudo.IDEIAS_ORIGINAIS, zupperEmissor, zupperReceptor);

        kudoRepository.saveAll(List.of(kudo1, kudo2, kudo3));

        MockHttpServletRequestBuilder request = get("/zupper/{id}/kudos/recebidos", zupperReceptor.getId());

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<KudoResponse> kudos = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                KudoResponse.class
        ));

        // Asserts
        assertThat(kudos)
                .hasSize(3);
        assertThat(kudos)
                .extracting(
                        "nome",
                        "enviadoPor"
                )
                .contains(
                        new Tuple(kudo1.getNome().name().toLowerCase(Locale.ROOT), kudo1.getEnviado().getNome()),
                        new Tuple(kudo2.getNome().name().toLowerCase(Locale.ROOT), kudo2.getEnviado().getNome()),
                        new Tuple(kudo3.getNome().name().toLowerCase(Locale.ROOT), kudo3.getEnviado().getNome())
                );

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando o zupper não tem kudos recebidos")
    void deveRetornarListaVaziaQuandoZupperNaoTemKudosRecebidos() throws Exception {

        // Cenário
        Zupper zupper = new Zupper("Antonio", "Desenvolvedor", LocalDate.now(), "antonio@zup.com.br");

        zupperRepository.save(zupper);

        MockHttpServletRequestBuilder request = get("/zupper/{id}/kudos/recebidos", zupper.getId());

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<KudoResponse> kudos = mapper.readValue(payloadResponse, typeFactory.constructCollectionType(
                List.class,
                KudoResponse.class
        ));

        // Asserts
        assertThat(kudos)
                .isEmpty();

    }

    @Test
    @DisplayName("Não deve listar kudos recebidos para um zupper não cadastrado")
    void naoDeveListarKudosRecebidosParaUmZupperNaoCadastrado() throws Exception {

        // Cenário
        MockHttpServletRequestBuilder request = get("/zupper/{id}/kudos/recebidos", Integer.MAX_VALUE);

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
        assertEquals("Zupper nao cadastrado", ((ResponseStatusException) resolvedException).getReason());

    }

}