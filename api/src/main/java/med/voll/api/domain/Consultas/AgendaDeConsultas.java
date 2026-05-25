package med.voll.api.domain.Consultas;

import med.voll.api.domain.Medico.Medico;
import med.voll.api.domain.Medico.MedicoRepository;
import med.voll.api.domain.Paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");

        }
        if (dados.idMedico() != null && !pacienteRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe");

        }

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepository.findById(dados.idMedico()).get();
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);
    }
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if  (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatorio quando o Medico não é escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade();

}
    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
