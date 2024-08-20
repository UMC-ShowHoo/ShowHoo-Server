package umc.ShowHoo.web.member.Service;

import umc.ShowHoo.web.member.entity.Member;

public interface MemberQueryService {
    Member getMemberByUid(Long uid);
}
