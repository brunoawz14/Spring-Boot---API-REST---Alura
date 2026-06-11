package med.voll.api.domain.Consultas.Validacoes.Agendamentos;

import med.voll.api.domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
// Classe com onjetivo de validar se a consulta está sendo agendada dentro do horário de funcionamento da clínica, que é das 7h às 18h, de segunda a sábado.
public class ValidadorHorarioFuncionamentoClinica implements  ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var Domingo =  dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoFechamentoDaClinica = dataConsulta.getHour() > 18;

        if (Domingo || antesDaAberturaDaClinica || depoisDoFechamentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
