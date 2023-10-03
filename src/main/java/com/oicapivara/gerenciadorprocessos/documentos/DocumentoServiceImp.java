package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import com.oicapivara.gerenciadorprocessos.processo.ProcessoRepository;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
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


    @Value("${file.upload.directory}")
    private String baseDir;



    @Override
    public String upload(Long id, MultipartFile file) {
        Optional<Processo> processoOptional = processoRepository.findById(id);
        Processo processo = processoOptional.orElseThrow( () -> new EntityNotFoundException("Processo não encontrado para o id: "+id));

        String numProcesso = processo.getNumeroProcesso();
        String originalFilename = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();

        String destinationDirectory = baseDir + numProcesso;
        try{
            Files.createDirectories(Paths.get(destinationDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de destino"+ e);
        }
        String destinationFilename = destinationDirectory + "/" + originalFilename + "_" + uuid + "." + fileExtension;

        try {
            Files.copy(file.getInputStream(),
                    Path.of(destinationFilename),
                    StandardCopyOption.REPLACE_EXISTING);

            Documento documento = documentoRepository.save(Documento.builder()
                    .nome(file.getOriginalFilename())
                    .extensao(file.getContentType())
                    .processo(processo)
                    .caminho(destinationFilename).build()
            );

            processo.addDocumento(documento);
            processoRepository.save(processo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return "documento enviado com sucesso " + destinationFilename;

    }
}
