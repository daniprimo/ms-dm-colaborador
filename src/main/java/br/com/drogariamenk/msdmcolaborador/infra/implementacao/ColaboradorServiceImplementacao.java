package br.com.drogariamenk.msdmcolaborador.infra.implementacao;

import br.com.drogariamenk.msdmcolaborador.aplicacao.exception.*;
import br.com.drogariamenk.msdmcolaborador.aplicacao.service.ColaboradorService;
import br.com.drogariamenk.msdmcolaborador.dominio.CPF;
import br.com.drogariamenk.msdmcolaborador.dominio.Colaborador;
import br.com.drogariamenk.msdmcolaborador.dominio.Endereco;
import br.com.drogariamenk.msdmcolaborador.dto.DadosTransferenciaColaboradorDTO;
import br.com.drogariamenk.msdmcolaborador.dto.request.AtualizarColaboradorRequest;
import br.com.drogariamenk.msdmcolaborador.dto.request.ColaboradorSalvarRequest;
import br.com.drogariamenk.msdmcolaborador.infra.mensageria.NovaRequisicaoColaboradorPublisher;
import br.com.drogariamenk.msdmcolaborador.infra.repository.ColaboradorRepository;
import br.com.drogariamenk.msdmcolaborador.infra.repository.JdbcTabelaDeClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorServiceImplementacao implements ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private JdbcTabelaDeClienteRepository jdbcClienteRepository;

    @Autowired
    private NovaRequisicaoColaboradorPublisher colaboradorPublisher;

    @Override
    public Object salvarColaborador(ColaboradorSalvarRequest request) {

        try {
            CPF cpf = new CPF(request.getCpf());
            verificarSeCpfEInexistente(request.getCpf());
            verificarSeTelefoneEInexistente(request.getTelefone());
        } catch (CpfJaExistenteException | TelefoneJaExistenteException | CpfInvalidoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getNomeDaRua(),
                request.getBairro());

        Colaborador colaborador = new Colaborador();
        colaborador.setNome(request.getNome());
        colaborador.setCpf(request.getCpf());
        colaborador.setTelefone(request.getTelefone());
        colaborador.setEndereco(endereco);
        colaborador.setComplementoDoEndereco(request.getComplemento());
        colaborador.criptografiaDosDadosSensiveis();


        Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);
        colaboradorSalvo.descriptografiaDosDadosSensiveis();

        DadosTransferenciaColaboradorDTO dadosTransferenciaColaboradorDTO = new DadosTransferenciaColaboradorDTO(
                colaboradorSalvo.getId(),
                colaboradorSalvo.getNome(),
                colaboradorSalvo.getCpf(),
                colaboradorSalvo.getTelefone(),
                "NOVO_COLABORADOR"
        );
        try {
            colaboradorPublisher.novaRequisicaoDeColaborador(dadosTransferenciaColaboradorDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }


        return colaboradorSalvo;


    }

    @Override
    public Object listarColaboradores() {
        return colaboradorRepository
                .findAll()
                .stream()
                .map(Colaborador::descriptografiaDosDadosSensiveis)
                .toList();
    }

    @Override
    public Object buscarColaboradorPeloTelefone(String telefone) {
        try {
            return jdbcClienteRepository.buscarClientePeloTelefone(telefone);
        } catch (TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            JSONObject json  = jsonObject.put("ERR: ", e.getMessage());
            return json.toString();
        }
    }

    @Override
    public Object buscarColaboradorPeloCpf(String cpf) {
        try {
            return jdbcClienteRepository.buscarClientePeloCpf(cpf);
        } catch (CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            JSONObject json  = jsonObject.put("ERR: ", e.getMessage());
            return json.toString();
        }
    }

    @Override
    public Object atualizarColaboradorPeloCpf(String cpf, AtualizarColaboradorRequest request) {
        Colaborador colaborador;
        try {

            colaborador = jdbcClienteRepository.buscarClientePeloCpf(cpf);
            if (!request.getTelefone().equals(colaborador.getTelefone())) {
                verificarSeTelefoneEInexistente(request.getTelefone());
            }

        } catch (TelefoneJaExistenteException | CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        colaborador.setNome(request.getNome());
        colaborador.setTelefone(request.getTelefone());
        colaborador.setComplementoDoEndereco(request.getComplementoDoEndereco());

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getRua(),
                ""
        );
        colaborador.setEndereco(endereco);
        colaborador.criptografiaDosDadosSensiveis();

        Colaborador clienteAtualizado = colaboradorRepository.save(colaborador);
        clienteAtualizado.descriptografiaDosDadosSensiveis();

        DadosTransferenciaColaboradorDTO dadosTransferenciaColaboradorDTO = new DadosTransferenciaColaboradorDTO(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getTelefone(),

                "ATUALIZAR_COLABORADOR");
        try {
            colaboradorPublisher.novaRequisicaoDeColaborador(dadosTransferenciaColaboradorDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }

        return clienteAtualizado;
    }

    @Override
    public Object atualizarColaboradorPeloTelefone(String telefone, AtualizarColaboradorRequest request) {
        Colaborador colaborador;
        try {

            colaborador = jdbcClienteRepository.buscarClientePeloTelefone(telefone);
            if (!request.getTelefone().equals(colaborador.getTelefone())) {
                verificarSeTelefoneEInexistente(request.getTelefone());
            }

        } catch (TelefoneJaExistenteException | TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        colaborador.setNome(request.getNome());
        colaborador.setTelefone(request.getTelefone());
        colaborador.setComplementoDoEndereco(request.getComplementoDoEndereco());

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getRua(),
                ""
        );
        colaborador.setEndereco(endereco);
        colaborador.criptografiaDosDadosSensiveis();

        Colaborador clienteAtualizado = colaboradorRepository.save(colaborador);
        clienteAtualizado.descriptografiaDosDadosSensiveis();

        DadosTransferenciaColaboradorDTO dadosTransferenciaColaboradorDTO = new DadosTransferenciaColaboradorDTO(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getTelefone(),

                "ATUALIZAR_COLABORADOR");
        try {
            colaboradorPublisher.novaRequisicaoDeColaborador(dadosTransferenciaColaboradorDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }

        return clienteAtualizado;
    }

    @Override
    public Object deletarColaboradorPeloTelefone(String telefone) {
        Colaborador cliente;
        try {
            cliente = jdbcClienteRepository.buscarClientePeloTelefone(telefone);
        } catch (TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        colaboradorRepository.delete(cliente);

        DadosTransferenciaColaboradorDTO dadosTransferenciaColaboradorDTO = new DadosTransferenciaColaboradorDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                "DELETAR_COLABORADOR");
        try {
            colaboradorPublisher.novaRequisicaoDeColaborador(dadosTransferenciaColaboradorDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DELETE", "COLABORADOR removido com sucesso.");
        return jsonObject.toString();

    }

    @Override
    public Object deletarColaboradorPeloCpf(String cpf) {
        Colaborador cliente;
        try {
            cliente = jdbcClienteRepository.buscarClientePeloCpf(cpf);
        } catch (CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        colaboradorRepository.delete(cliente);

        DadosTransferenciaColaboradorDTO dadosTransferenciaColaboradorDTO = new DadosTransferenciaColaboradorDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                "DELETAR_COLABORADOR");
        try {
            colaboradorPublisher.novaRequisicaoDeColaborador(dadosTransferenciaColaboradorDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DELETE", "COLABORADOR removido com sucesso.");
        return jsonObject.toString();

    }


    private void verificarSeCpfEInexistente(String cpf) throws CpfJaExistenteException {

        if (jdbcClienteRepository.isCpfExiste(cpf)) {
            throw new CpfJaExistenteException("CPF já existe em nossos cadastros.");
        }
    }

    private void verificarSeTelefoneEInexistente(String telefone) throws TelefoneJaExistenteException {

        if (jdbcClienteRepository.isTelefonexiste(telefone)) {
            throw new TelefoneJaExistenteException("Telefone já existe em nossos cadastros.");
        }


    }
}
