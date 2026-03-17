package med.voll.api.Paciente;

import aj.org.objectweb.asm.commons.Remapper;
import med.voll.api.Medico.Medico;
import med.voll.api.Medico.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}
