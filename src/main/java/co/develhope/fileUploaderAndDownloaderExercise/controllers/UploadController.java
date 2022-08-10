package co.develhope.fileUploaderAndDownloaderExercise.controllers;

import co.develhope.fileUploaderAndDownloaderExercise.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//a controller REST for upload
@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws  Exception{
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String singleUploadedFileName = fileStorageService.upload(file);
            fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }
}
