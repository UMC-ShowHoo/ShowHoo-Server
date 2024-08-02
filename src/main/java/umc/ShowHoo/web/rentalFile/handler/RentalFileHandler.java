package umc.ShowHoo.web.rentalFile.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class RentalFileHandler extends GeneralException {
    public RentalFileHandler(BaseErrorCode errorCode){super((errorCode));}
}
