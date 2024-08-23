package umc.ShowHoo.web.spaceApply.exception.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class SpaceApplyHandler extends GeneralException {
    public SpaceApplyHandler(BaseErrorCode errorCode) {super(errorCode);}
}
