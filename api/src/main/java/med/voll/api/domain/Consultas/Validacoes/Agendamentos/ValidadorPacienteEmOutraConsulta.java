package med.voll.api.domain.Consultas.Validacoes.Agendamentos;

import med.voll.api.domain.Consultas.ConsultaRepository;
import med.voll.api.domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteEmOutraConsulta implements ValidadorAgendamentoDeConsulta {


    @Autowired
    private ConsultaRepository repository;


public void  validar(DadosAgendamentoConsulta dados) {
        // Lógica para verificar se o paciente já tem uma consulta agendada no mesmo horário
        // Se tiver, lançar uma ValidacaoException com uma mensagem apropriada
        var primeroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacienteComOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeroHorario, ultimoHorario);
        if (pacienteComOutraConsulta) {
            throw new ValidacaoException("Paciente já tem uma consulta agendada nesse horário");
        }
    }
}
