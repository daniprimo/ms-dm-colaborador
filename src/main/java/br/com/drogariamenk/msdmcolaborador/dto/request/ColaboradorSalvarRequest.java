package br.com.drogariamenk.msdmcolaborador.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorSalvarRequest {

    private String nome;
    private String cpf;
    private String telefone;
    private String cep;
    private String nomeDaRua;
    private String bairro;
    private String numero;
    private String complemento;
}
