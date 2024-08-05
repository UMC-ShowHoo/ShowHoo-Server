package umc.ShowHoo.apiPayload.exception.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class BookHandler extends GeneralException {

    public BookHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}