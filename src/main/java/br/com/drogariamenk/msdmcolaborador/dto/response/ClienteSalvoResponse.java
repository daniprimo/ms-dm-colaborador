package br.com.drogariamenk.msdmcolaborador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteSalvoResponse {

    private Long idDoCliente;
    private String mensagem;
}
