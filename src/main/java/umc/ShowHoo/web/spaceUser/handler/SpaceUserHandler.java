package umc.ShowHoo.web.spaceUser.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class SpaceUserHandler extends GeneralException {
    public SpaceUserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
