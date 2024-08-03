package umc.ShowHoo.web.performerProfile.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class PerformerProfileHandler extends GeneralException {
    public PerformerProfileHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
