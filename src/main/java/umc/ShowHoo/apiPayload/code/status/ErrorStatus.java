package umc.ShowHoo.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    //Exception 핸들링 테스트
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "에러 핸들링 테스트"),

    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER001", "member not found"),

    //SPACE
    SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE001", "Space not found"),

    //SPACEUSER
    SPACEUSER_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACEUSER001", "SpaceUser not found"),

    //AUDIENCE
    AUDIENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "AUDIENCE001", "Audience not found"),

    //BOOK
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK001", "Book not found"),

    //PERFORMER
    PERFORMER_NOT_FOUND(HttpStatus.NOT_FOUND, "PERFORMER001", "Performer not found"),

    //PERFORMER PROFILE
    PERFORMER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PERFORMER_PROFILE001", "Performer Profile not found"),

    //SHOW
    SHOW_NOT_FOUND(HttpStatus.NOT_FOUND,"SHOW001","Show not found"),

    //SHOWS PREFER
    SHOWS_PREFER_NOT_FOUND(HttpStatus.NOT_FOUND,"SHOWS_PREFER001", "ShowsPrefer not found"),

    //SPACE PREFER
    SPACE_PREFER_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE_PREFER001", "SpacePrefer not found"),

    //SPACE_APPLY
    SPACE_APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE_APPLY001", "SpaceApply not found"),

    //SPACE_REVIEW
    SPACE_REVIEW_PERMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "SPACE_REVIEW001", "SpaceReviewPermission not found"),

    //RENTALFILE
    RENTALFILE_FORM_NOT_FOUND(HttpStatus.NOT_FOUND,"RENTALFILE_FORM001","RentalFile form not found"),

    RENTALFILE_NOT_FOUND(HttpStatus.NOT_FOUND,"RENTALFILE001","RentalFile not found"),

    //NOTIFICATION
    NOTIFICATION_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"NOTIFICATION001","정의되지 않은 notification type 입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }

}
