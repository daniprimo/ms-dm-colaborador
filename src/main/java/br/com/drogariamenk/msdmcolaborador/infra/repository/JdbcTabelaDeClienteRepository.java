package br.com.drogariamenk.msdmcolaborador.infra.repository;


import br.com.drogariamenk.msdmcolaborador.aplicacao.exception.CpfNaoEncontradoException;
import br.com.drogariamenk.msdmcolaborador.aplicacao.exception.TelefoneNaoEncontradoException;
import br.com.drogariamenk.msdmcolaborador.dominio.Colaborador;
import br.com.drogariamenk.msdmcolaborador.dto.RecuperandoCpfDTO;
import br.com.drogariamenk.msdmcolaborador.dto.mapper.BuscandoTodosOsClientesMapper;
import br.com.drogariamenk.msdmcolaborador.dto.mapper.ColaboradorMapper;
import br.com.drogariamenk.msdmcolaborador.dto.mapper.TelefoneMapper;
import br.com.drogariamenk.msdmcolaborador.infra.util.CriptografiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class JdbcTabelaDeClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CriptografiaService criptografiaService;


    public boolean isCpfExiste(String cpf) {
        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.cpf FROM colaborador cs;", new ColaboradorMapper());
        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            if (cpf.equals(criptografiaService.descriptografar(recuperandoCpfDTO.getCpf()))) {
                    return true;
            }
        }
        return false;
    }

    public boolean isTelefonexiste(String telefone) {

        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.telefone FROM colaborador cs;", new TelefoneMapper());
        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            if (telefone.equals(criptografiaService.descriptografar(recuperandoCpfDTO.getCpf()))) {
                return true;
            }
        }
        return false;

    }

    public Colaborador buscarClientePeloCpf(String cpf) throws CpfNaoEncontradoException {

            List<Colaborador> query = jdbcTemplate.query("SELECT * FROM colaborador cs;", new BuscandoTodosOsClientesMapper());

        for (Colaborador colaborador : query) {
            Colaborador colaboradorDescriptografado = colaborador.descriptografiaDosDadosSensiveis();
            if (cpf.equals(colaboradorDescriptografado.getCpf())) {
                return colaborador;
            }
        }

        throw new CpfNaoEncontradoException("CPF não consta em nossos registros.");


    }

    public Colaborador buscarClientePeloTelefone(String telefone) throws TelefoneNaoEncontradoException {

        List<Colaborador> query = jdbcTemplate.query("SELECT * FROM colaborador cs;", new BuscandoTodosOsClientesMapper());

        for (Colaborador colaborador : query) {
            Colaborador colaboradorDescriptografado = colaborador.descriptografiaDosDadosSensiveis();
            if (telefone.equals(colaboradorDescriptografado.getTelefone())) {
                return colaborador;
            }
        }

        throw new TelefoneNaoEncontradoException("Telefone não consta em nossos registros.");


    }

}
