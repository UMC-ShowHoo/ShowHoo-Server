package umc.ShowHoo.web.spacePhoto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.aws.s3.AmazonS3Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpacePhotoService {

    private final AmazonS3Manager amazonS3Manager;

    public List<String> uploadPhotos(List<MultipartFile> photos) {
        List<String> photoUrls = new ArrayList<>();

        for (MultipartFile photo : photos) {
            String uuid = UUID.randomUUID().toString();
            String keyName = "spaceRegister/" + uuid;
            String imageUrl = amazonS3Manager.uploadFile(keyName, photo);
            photoUrls.add(imageUrl);
        }

        return photoUrls;
    }
}
