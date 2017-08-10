package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//Classe responsável para salvar o arquivo

@Component //Injetar um component genererico, ou seja, não é dao nem controller.
public class FileSaver {

    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * @param baseFolder - Onde será salvo o arquivo
     * @param file - e o arquivo
     * @return
     */
    public String write(String baseFolder,MultipartFile file) {

        try {    

            String realPath = request.getServletContext().getRealPath("/" + baseFolder);
            String path = realPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(path)); 
            
            return baseFolder + "/" + file.getOriginalFilename();  //retornando o caminho relativo

        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);    
        }


    }
}