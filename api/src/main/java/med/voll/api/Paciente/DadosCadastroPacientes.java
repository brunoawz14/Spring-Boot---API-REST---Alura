package med.voll.api.Paciente;

import med.voll.api.Endereco.DadosEndereco;

public record DadosCadastroPacientes(String nome, String email, String telefone, String cpf, DadosEndereco endereco) {
}
