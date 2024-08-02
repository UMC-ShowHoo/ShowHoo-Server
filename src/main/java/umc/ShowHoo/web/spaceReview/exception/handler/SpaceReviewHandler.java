package umc.ShowHoo.web.spaceReview.exception.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class SpaceReviewHandler extends GeneralException {
    public SpaceReviewHandler(BaseErrorCode errorCode) {super(errorCode);}
}
