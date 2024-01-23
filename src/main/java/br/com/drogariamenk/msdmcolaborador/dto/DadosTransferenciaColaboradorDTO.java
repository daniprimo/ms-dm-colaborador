package br.com.drogariamenk.msdmcolaborador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DadosTransferenciaColaboradorDTO {

    private Long idDoCliente;
    private String nomeDoCliente;

    private String cpfDoCliente;

    private String telefoneDoCliente;

    private String tipoDeAcao;

}
