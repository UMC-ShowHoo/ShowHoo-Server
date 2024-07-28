package umc.ShowHoo.web.Show.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class ShowsHandler extends GeneralException {
    public ShowsHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
