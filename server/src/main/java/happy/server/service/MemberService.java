package happy.server.service;

import happy.server.entity.Address;
import happy.server.entity.Member;
import happy.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 정보 수정
    @Transactional
    public Long update(Member member) {
        memberRepository.update(member);
        log.info("{} 회원 정보 변경", member.getId());
        return member.getId();
    }

    // 로그인 기능
    public Member MemberLogin(Long memberId, String password) {
        return memberRepository.findByLoginId(memberId)
                .filter(m -> m.getPassword().equals(password)).orElse(null);
    }

    // 주소 변경
    @Transactional
    public Long updateAddress(Long memberId, Address address) {
        Member member = memberRepository.fineOne(memberId);
        member.setAddress(address);
        memberRepository.update(member);
        return member.getId();
    }

    // 중복 회원 검사
    public boolean validateDuplicateMember(Member member) {
        Member fineOne = memberRepository.fineOne(member.getId());
        return fineOne == null;
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
