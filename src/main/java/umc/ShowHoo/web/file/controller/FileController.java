package umc.ShowHoo.web.file.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.yaml.snakeyaml.introspector.PropertySubstitute.log;


@Controller
public class FileController {
    @Value("${file.dir}")
    private String fileDir; // 파일을 저장할 경로. 해당 경로에 폴더가 만들어져 있어야 한다.

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           // MultipartFile로 file을 전송받기
                           @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        //log.info("request={}", request);
        //log.info("itemName={}", itemName);
        //log.info("multipartFile={}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            //log.info("파일 저장 fullPath={}", fullPath);
            file.transferTo(new File(fullPath)); // file.transferTo를 통해 간편하게 파일을 저장할 수 있다
        }
        return "upload-form";
    }
//    @GetMapping("/performer/file")
//    public String getFileForm(){
//        return "file";
//    }
//
//    @GetMapping("/file-spring")
//    public String getSpringFileForm() {
//        return "file-spring";
//    }
//    @ResponseBody
//    @PostMapping("/file-spring")
//    public String registerFileForm(
//            @RequestParam String name,
//            @RequestParam MultipartFile file
//    ) throws IOException {
//
//        String fileDir = "/C:/files/";
//        file.transferTo(new File(fileDir + file.getOriginalFilename()));
//        return "ok";
//    }

    @PostMapping("/performer/fileupload")
    public String register(@RequestPart(value = "multipartFile") MultipartFile multipartFile)
        throws IOException{

    }

}
