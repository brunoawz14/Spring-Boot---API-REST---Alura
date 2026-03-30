package med.voll.api.domain.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//Dto
public record DadosEndereco(
        @NotBlank
        String logradouro,

        String numero,

        String complemento,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep ,
        @NotBlank
        String bairro,
        @NotBlank
        String cidade,
        @NotBlank
        String uf
)
{

}
