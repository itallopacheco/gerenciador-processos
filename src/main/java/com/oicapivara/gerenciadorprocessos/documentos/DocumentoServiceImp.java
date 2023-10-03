package com.oicapivara.gerenciadorprocessos.documentos;

import com.oicapivara.gerenciadorprocessos.exceptions.EntityNotFoundException;
import com.oicapivara.gerenciadorprocessos.processo.Processo;
import com.oicapivara.gerenciadorprocessos.processo.ProcessoRepository;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentoServiceImp implements DocumentoService{

    @Autowired
    DocumentoRepository documentoRepository;
    @Autowired
    ProcessoRepository processoRepository;

    @Autowired
    private ServletContext servletContext;

    @Value("${file.upload.directory}")
    private String baseDir;

    private String getAbsPath(){
        String workingDir = servletContext.getRealPath("/");
        return workingDir + File.separator + baseDir;
    }


    @Override
    public String upload(Long id, MultipartFile file) {
        String filePath = getAbsPath() + file.getOriginalFilename();
        Optional<Processo> processoOptional = processoRepository.findById(id);
        Processo processo = processoOptional.get();
        if (processoOptional.isEmpty())throw new EntityNotFoundException("Processo não encontrado para o id: "+id);

        Documento documento = documentoRepository.save(Documento.builder()
                .nome(file.getOriginalFilename())
                .extensao(file.getContentType())
                .processo(processo)
                .caminho(filePath).build()
        );

        processo.addDocumento(documento);
        processoRepository.save(processo);


        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (documento != null){
            return "documento enviado com sucesso " + filePath;
        }
        return null;
    }
}
