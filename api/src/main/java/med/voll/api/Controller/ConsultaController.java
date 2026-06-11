package med.voll.api.Controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.Consultas.AgendaDeConsultas;
import med.voll.api.domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.Consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.Medico.DadosDetalhamentoMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendar;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendar.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null,  null, null));
    }
}
