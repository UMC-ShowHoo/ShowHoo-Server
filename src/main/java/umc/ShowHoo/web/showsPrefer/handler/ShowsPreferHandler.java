package umc.ShowHoo.web.showsPrefer.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class ShowsPreferHandler extends GeneralException {

    public ShowsPreferHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
