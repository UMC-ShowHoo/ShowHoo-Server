package umc.ShowHoo.web.file.service;

import org.springframework.stereotype.Service;

@Service
public class fileService {
    //서버에 파일 저장 & DB에 파일 정보 저장
    String originalFilename = multipartFile.getOriginalFilename();
    String saveFileName = createSaveFileName(originalFilename);
}
