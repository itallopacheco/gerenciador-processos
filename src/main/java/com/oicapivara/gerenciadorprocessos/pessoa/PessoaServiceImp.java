package com.oicapivara.gerenciadorprocessos.pessoa;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.exceptions.LawyerCreationException;
import com.oicapivara.gerenciadorprocessos.exceptions.PasswordMatchesException;
import com.oicapivara.gerenciadorprocessos.exceptions.UniqueFieldException;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.AuthenticationDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.CreatePessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.PessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.dto.UpdatePessoaDTO;
import com.oicapivara.gerenciadorprocessos.pessoa.enums.PessoaRole;
import com.oicapivara.gerenciadorprocessos.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImp implements PessoaService{

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String login(AuthenticationDTO authenticationDTO) {
        var userAndPassword = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),authenticationDTO.getPassword());
        var auth = authenticationManager.authenticate(userAndPassword);
        var token = tokenService.generateToken((Pessoa) auth.getPrincipal());
        return token;
    }

    @Override
    public PessoaDTO create(CreatePessoaDTO dto) {
        validatePasswordMatching(dto);
        validateUniqueCpf(dto.getCpf());
        validateOabForAdvogado(dto);

        Pessoa pessoa = dto.dtoToObject();
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }

    @Override
    public PessoaDTO getById(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) throw new EntityNotFoundException("Pessoa nao encontrada para o id: " + id);
        Pessoa pessoa = pessoaOptional.get();
        return new PessoaDTO(pessoa);
    }

    @Override
    public PessoaDTO getByCpf(String cpf) {
        Pessoa pessoaOptional = pessoaRepository.findByCpf(cpf);
        if (pessoaOptional == null) throw new EntityNotFoundException("Pessoa nao encontrada para o cpf: " + cpf);
        return new PessoaDTO(pessoaOptional);
    }

    @Override
    public Page<PessoaDTO> getAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name"
        );
        PageImpl<Pessoa> pessoaPage = new PageImpl<>(pessoaRepository.findAll(),pageRequest,size);
        Page<PessoaDTO> pessoaDTOS = pessoaPage.map(pessoa -> new PessoaDTO(pessoa));
        return pessoaDTOS;
    }

    @Override
    public Page<PessoaDTO> search(String searchTerm, Integer page,Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.ASC, "name");
        Page<Pessoa> pessoaPage = pessoaRepository.search(searchTerm, pageRequest);
        Page<PessoaDTO> pessoaDTOS = pessoaPage.map(pessoa -> new PessoaDTO(pessoa));
        return pessoaDTOS;
    }

    @Override
    public PessoaDTO update(Long id, UpdatePessoaDTO dto) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) throw new EntityNotFoundException("Pessoa não encontrada para o id: " + id);
        Pessoa pessoa = pessoaOptional.get();

        if (dto.getName() != null){
            pessoa.setName(dto.getName());
        }
        pessoaRepository.save(pessoa);

        return new PessoaDTO(pessoa);
    }

    @Override
    public Void delete(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) throw new EntityNotFoundException("Pessoa não encontrada para id: "+id);
        Pessoa pessoa = pessoaOptional.get();
        pessoaRepository.delete(pessoa);
        return null;
    }


    private void validatePasswordMatching(CreatePessoaDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new PasswordMatchesException("As senhas não coincidem.");
        }
    }
    private void validateOabForAdvogado(CreatePessoaDTO dto) {
        if (dto.getRoleList().contains(PessoaRole.ADVOGADO) && (dto.getOab() == null || dto.getOab().isEmpty())) {
            throw new LawyerCreationException("O campo OAB é obrigatório para criar um usuário do tipo Advogado.");
        }
    }

    private void validateUniqueCpf(String cpf) {
        boolean cpfExists = pessoaRepository.existsByCpf(cpf);
        if (cpfExists) {
            throw new UniqueFieldException("CPF já cadastrado para o valor: " + cpf);
        }
    }


}
