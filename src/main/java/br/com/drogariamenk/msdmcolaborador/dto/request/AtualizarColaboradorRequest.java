package br.com.drogariamenk.msdmcolaborador.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarColaboradorRequest {

    private String nome;
    private String telefone;

    private String cep;
    private String rua;
    private String numero;
    private String complementoDoEndereco;



}
