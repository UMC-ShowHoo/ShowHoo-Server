package umc.ShowHoo.web.book.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class BookHandler extends GeneralException {

    public BookHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
