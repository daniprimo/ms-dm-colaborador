package br.com.drogariamenk.msdmcolaborador.dto.mapper;


import br.com.drogariamenk.msdmcolaborador.dominio.Colaborador;
import br.com.drogariamenk.msdmcolaborador.dominio.Endereco;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscandoTodosOsClientesMapper implements RowMapper<Colaborador> {

    @Override
    public Colaborador mapRow(ResultSet rs, int rowNum) throws SQLException {
        Colaborador colaborador = new Colaborador();
        colaborador.setId(rs.getLong("id"));
        colaborador.setCpf(rs.getString("cpf"));
        colaborador.setTelefone(rs.getString("telefone"));
        colaborador.setNome(rs.getString("nome"));
        colaborador.setComplementoDoEndereco(rs.getString("complemento_do_endereco"));

        Endereco endereco = new Endereco();
        endereco.setCep(rs.getString("cep"));
        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setLocalidade(rs.getString("localidade"));
        endereco.setUf(rs.getString("uf"));
        colaborador.setEndereco(endereco);
        return colaborador;
    }
}
