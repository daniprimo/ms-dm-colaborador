package br.com.drogariamenk.msdmcolaborador.dto.mapper;

import br.com.drogariamenk.msdmcolaborador.dto.RecuperandoCpfDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColaboradorMapper implements RowMapper<RecuperandoCpfDTO> {
    @Override
    public RecuperandoCpfDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecuperandoCpfDTO recuperandoCpfDTO = new RecuperandoCpfDTO();
        String cpf = "";
        cpf = rs.getString("cpf");
        recuperandoCpfDTO.setCpf(cpf);
        System.out.println(recuperandoCpfDTO.toString());
        return recuperandoCpfDTO;
    }
}
