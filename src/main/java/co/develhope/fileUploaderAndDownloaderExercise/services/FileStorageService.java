package co.develhope.fileUploaderAndDownloaderExercise.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    //upload endpoint to upload a file
    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;
        //Verifications on the repository folder
        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("Final folder does not exists");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");

        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    //download endpoint to download a file
    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepository.exists()) throw new IOException("File does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }
}
