package med.voll.api.domain.Consultas.Validacoes.Cancelamento;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Consultas.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
