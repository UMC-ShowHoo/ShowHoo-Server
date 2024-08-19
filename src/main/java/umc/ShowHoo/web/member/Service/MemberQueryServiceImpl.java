package umc.ShowHoo.web.member.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.member.handler.MemberHandler;
import umc.ShowHoo.web.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberByUid(Long uid){
        return memberRepository.findByUid(uid)
                .orElseThrow(()->new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
