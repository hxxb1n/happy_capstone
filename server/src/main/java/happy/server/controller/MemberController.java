package happy.server.controller;

import happy.server.entity.Member;
import happy.server.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("members/join")
    public CreateMemberResponse joinMember(@RequestBody @Validated CreateMemberRequest request) {
        Member member = new Member();
        member.setId(request.getId());
        member.setName(request.getName());
        member.setPassword(request.getPassword());
        memberService.join(member);
        return new CreateMemberResponse(member.getId());
    }

    @Data
    public static class CreateMemberRequest {
        private Long id;
        private String name;
        private String password;
    }

    @Data
    public static class CreateMemberResponse {
        private Long memberId;
        public CreateMemberResponse(Long memberId) {
            this.memberId = memberId;
        }
    }

}
