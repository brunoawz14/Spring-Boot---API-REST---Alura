package med.voll.api.domain.Consultas.Validacoes.Agendamentos;

import med.voll.api.domain.Consultas.ConsultaRepository;
import med.voll.api.domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoEmOutraConsulta implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Lógica para verificar se o médico já tem outra consulta agendada no mesmo horário
        // Se houver conflito, lançar uma ValidacaoException com uma mensagem apropriada
        var MedicoEmOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),  dados.data());
        if (MedicoEmOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("O médico já tem outra consulta agendada no mesmo horário.");
        }
    }
}
