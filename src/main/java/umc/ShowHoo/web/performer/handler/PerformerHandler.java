package umc.ShowHoo.web.performer.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class PerformerHandler extends GeneralException {
    public PerformerHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
