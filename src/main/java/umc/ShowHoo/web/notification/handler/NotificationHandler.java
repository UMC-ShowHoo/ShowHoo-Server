package umc.ShowHoo.web.notification.handler;

import umc.ShowHoo.apiPayload.code.BaseErrorCode;
import umc.ShowHoo.apiPayload.exception.GeneralException;

public class NotificationHandler extends GeneralException {
    public NotificationHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
