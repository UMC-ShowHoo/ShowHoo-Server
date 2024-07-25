package umc.ShowHoo.web.space.exception.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class SpaceHandler extends GeneralException {
    public SpaceHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
