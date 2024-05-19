package happy.server.api;

import happy.server.dto.AddressUpdateRequest;
import happy.server.entity.Address;
import happy.server.entity.Authority;
import happy.server.entity.Member;
import happy.server.repository.MemberRepository;
import happy.server.service.MemberService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/api/members/join")
    public CreateMemberResponse joinMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setId(request.getId());
        member.setName(request.getName());
        member.setPassword(request.getPassword());
        // 첫 권한은 유저로 설정
        member.setAuthority(Authority.USER);
        boolean b = memberService.validateDuplicateMember(member);
        if (!b) {
            log.info("중복된 아이디로 가입 시도");
            return new CreateMemberResponse("1");
        } else {
            memberService.join(member);
            log.info("회원 가입 완료: {}", member.getName());
            return new CreateMemberResponse(member.getName());
        }
    }

    @PostMapping("/api/members/login")
    public CreateMemberResponse login(@RequestBody @Valid CreateMemberRequest request) {
        Member loginMember = memberService.MemberLogin(request.getId(), request.getPassword());
        if (loginMember != null) {
            log.info("{} 로그인 성공", request.getId());
            return new CreateMemberResponse(loginMember.getId(), loginMember.getName(), loginMember.getAuthority());
        } else {
            throw new IllegalStateException("로그인 실패");
        }
    }

    @GetMapping("/api/members/{memberId}")
    public Member getMemberById(@PathVariable Long memberId) {
        return memberService.fineOne(memberId);
    }

    @PutMapping("/api/members/updateAddress")
    public void updateAddress(@RequestBody AddressUpdateRequest addressUpdateRequest) {
        memberService.updateAddress(addressUpdateRequest.getMemberId(), addressUpdateRequest.getAddress());
    }

    @Data
    public static class CreateMemberRequest {
        private Long id;
        private String name;
        private String password;
    }

    @Data
    public static class CreateMemberResponse {
        private String name;
        private Long id;
        private Authority authority;
        private Address address;
        public CreateMemberResponse(String name) {
            this.name = name;
        }

        public CreateMemberResponse(String name, Authority authority) {
            this.name = name;
            this.authority = authority;
        }

        public CreateMemberResponse(Long id, String name, Authority authority) {
            this.id = id;
            this.name = name;
            this.authority = authority;
        }

        public CreateMemberResponse(String name, Long id, Authority authority, Address address) {
            this.name = name;
            this.id = id;
            this.authority = authority;
            this.address = address;
        }

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
