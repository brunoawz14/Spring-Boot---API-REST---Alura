package med.voll.api.Medico;

import med.voll.api.Medico.Especialidade;
import med.voll.api.Endereco.DadosEndereco;
//Dto
public record DadosCadastroMedico (String nome,String email,String crm,Especialidade especialidade, DadosEndereco endereco) {
}
