package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.documentos.dto.DocumentoDTO;
import com.oicapivara.gerenciadorprocessos.documentos.dto.UpdateDocumentoDTO;
import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.pessoa.Pessoa;
import com.oicapivara.gerenciadorprocessos.pessoa.PessoaRepository;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import com.oicapivara.gerenciadorprocessos.processo.ProcessoRepository;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentoServiceImp implements DocumentoService{

    @Autowired
    DocumentoRepository documentoRepository;
    @Autowired
    ProcessoRepository processoRepository;
    @Autowired
    PessoaRepository pessoaRepository;


    @Value("${file.upload.directory}")
    private String baseDir;



    @Override
    public String create(Long id, MultipartFile file, Long userId) {
        Optional<Processo> processoOptional = processoRepository.findById(id);
        Processo processo = processoOptional.orElseThrow( () -> new EntityNotFoundException("Processo não encontrado para o id: "+id));

        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(userId);
        Pessoa pessoa = pessoaOptional.orElseThrow( () -> new EntityNotFoundException("Pessoa não encontrada para o id: "+ id ));

        String numProcesso = processo.getNumeroProcesso();
        String originalFilename = file.getOriginalFilename();
        String filenameWithoutExtension = FilenameUtils.removeExtension(originalFilename);
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();

        String destinationDirectory = baseDir + numProcesso;
        try{
            Files.createDirectories(Paths.get(destinationDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de destino"+ e);
        }
        String destinationFilename = destinationDirectory + "/" + filenameWithoutExtension + "_" + uuid + "." + fileExtension;

        try {
            Files.copy(file.getInputStream(),
                    Path.of(destinationFilename),
                    StandardCopyOption.REPLACE_EXISTING);

            Documento documento = documentoRepository.save(Documento.builder()
                    .nome(originalFilename)
                    .extensao(file.getContentType())
                    .processo(processo)
                    .caminho(destinationFilename)
                    .ativo(true)
                    .proprietario(pessoa)
                    .build()
            );

            processo.addDocumento(documento);
            processoRepository.save(processo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return "documento enviado com sucesso " + destinationFilename;

    }

    @Override
    public Resource getById(Long id) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        if (documentoOptional.isEmpty()) throw new EntityNotFoundException("Documento não encontrado para o id:" +id);
        Documento documento = documentoOptional.get();
        Path pathToFile = Path.of(documento.getCaminho());
        UrlResource resource = null;
        try {
            resource = new UrlResource(pathToFile.toUri());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resource;

    }

    @Override
    public DocumentoDTO update(Long id, UpdateDocumentoDTO dto) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        if (documentoOptional.isEmpty()) throw new EntityNotFoundException("Documento não encontrado para o id:" +id);
        Documento documento = documentoOptional.get();

        if(dto.getAtivo() != null){
            documento.setAtivo(dto.getAtivo());
        }
        documentoRepository.save(documento);
        DocumentoDTO response = new DocumentoDTO(documento);
        return response;
    }

    @Override
    public void delete(Long id) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        if (documentoOptional.isEmpty()) throw new EntityNotFoundException("Documento não encontrado para o id:" +id);
        Documento documento = documentoOptional.get();

        documentoRepository.delete(documento);
    }


}
