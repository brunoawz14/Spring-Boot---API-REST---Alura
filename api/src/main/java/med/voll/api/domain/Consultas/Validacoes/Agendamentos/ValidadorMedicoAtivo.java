package med.voll.api.domain.Consultas.Validacoes.Agendamentos;

import med.voll.api.domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.Medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo  implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }
        var  medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new IllegalArgumentException("Médico não está ativo no sistema");
        }
    }
}
