package med.voll.api.domain.Consultas;

import med.voll.api.domain.Paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);
    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime dataInicio, LocalDateTime dataFim);


}
