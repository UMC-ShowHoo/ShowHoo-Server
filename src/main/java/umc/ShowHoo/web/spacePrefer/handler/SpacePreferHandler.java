package umc.ShowHoo.web.spacePrefer.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class SpacePreferHandler extends GeneralException {
    public SpacePreferHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
