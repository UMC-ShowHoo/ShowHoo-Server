package umc.ShowHoo.apiPayload.exception.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class AudienceHandler extends GeneralException {

    public AudienceHandler(BaseErrorCode errorCode) {super(errorCode);}
}
