package med.voll.api.Controller;


import med.voll.api.Medico.DadosCadastroMedico;
import med.voll.api.Medico.Medico;
import med.voll.api.Medico.MedicoRepository;
import med.voll.api.Paciente.DadosCadastroPacientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;


    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        repository.save(new Medico(dados));

    }

}
