package happy.server.service;

import happy.server.entity.Member;
import happy.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검사
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.fineByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 한 명 조회
    public Member fineOne(Long id) {
        return memberRepository.fineOne(id);
    }

    // 회원 탈퇴
    @Transactional
    public void leave(Member member) {
        memberRepository.delete(member);
    }

}
