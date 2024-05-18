package happy.server.api;

import happy.server.entity.Authority;
import happy.server.entity.Member;
import happy.server.repository.MemberRepository;
import happy.server.service.MemberService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("members/join")
    public CreateMemberResponse joinMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setId(request.getId());
        member.setName(request.getName());
        member.setPassword(request.getPassword());
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

    @PostMapping("members/login")
    public CreateMemberResponse login(@RequestBody @Valid CreateMemberRequest request) {
        Member loginMember = memberService.MemberLogin(request.getId(), request.getPassword());
        if (loginMember != null) {
            log.info("{} 로그인 성공", request.getId());
            return new CreateMemberResponse(loginMember.getName(), loginMember.getAuthority());
        } else {
            throw new IllegalStateException("로그인 실패");
        }
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
        public CreateMemberResponse(String name) {
            this.name = name;
        }

        public CreateMemberResponse(String name, Authority authority) {
            this.name = name;
            this.authority = authority;
        }

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
