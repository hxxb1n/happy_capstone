package happy.server.controller;

import happy.server.entity.Authority;
import happy.server.entity.Member;
import happy.server.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setId(form.getId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAuthority(Authority.USER);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/auth")
    public String showAuthForm(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        model.addAttribute("authorityForm", new MemberForm());
        return "members/authorityForm";
    }

    @PostMapping("/members/auth")
    public String updateAuthority(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/authorityForm";
        }
        Member member = memberService.fineOne(form.getId());
        if (member != null) {
            member.setAuthority(form.getAuthority());
            memberService.update(member);
        }
        return "redirect:/members";
    }

}
