package umc.ShowHoo.web.spacePhoto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpacePhotoRequestDTO {
    private URL photoUrl;
}
