package med.voll.api.Controller;

import med.voll.api.domain.Endereco.DadosEndereco;
import med.voll.api.domain.Endereco.Endereco;
import med.voll.api.domain.Medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private  JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJacksonTester;

    @MockitoBean
    private MedicoRepository repository;

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                null,
                null,
                "00000000",
                "bairro",
                "Brasilia",
                "DF"
        );
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 quando as info são invalidas")
    @WithMockUser
    void cadastro_medico () throws Exception {
        var response = mvc
        .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando informacoes estao validas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var dadosCadastro = new DadosCadastroMedico(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                dadosEndereco());

        // 1. Criamos a instância do Médico baseada nos dados de cadastro
        var medicoSalvo = new Medico(dadosCadastro);

        // 2. Simulamos o ID que o banco de dados geraria automaticamente (ex: 1L)
        // Nota: Se a sua classe Medico tiver um método setId() ou um construtor com ID, pode usar diretamente.
        // Caso contrário, a ferramenta do Spring abaixo injeta o valor diretamente no atributo privado:
        org.springframework.test.util.ReflectionTestUtils.setField(medicoSalvo, "id", 1L);

        // 3. Ajustamos o mock para devolver este médico que já possui um ID
        when(repository.save(any())).thenReturn(medicoSalvo);

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJacksonTester.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        // 4. No detalhamento esperado, trocamos o 'null' pelo ID '1L' que a API deve devolver
        var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoMedicoJacksonTester.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


}
