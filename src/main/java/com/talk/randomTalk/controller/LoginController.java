package com.talk.randomTalk.controller;

import com.talk.randomTalk.domain.Member;
import com.talk.randomTalk.form.LoginForm;
import com.talk.randomTalk.form.MemberForm;
import com.talk.randomTalk.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("signUp")
    public String signUp(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/signUp";
    }

    @PostMapping("signUp")
    public String signUp(@Validated @ModelAttribute MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        Member member = new Member();
        member.setId(memberForm.getId());
        member.setName(memberForm.getName());
        member.setPassword(memberForm.getPassword());
        member.setEMail(memberForm.getEmail());

        memberService.join(member);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "member/login";
    }

    @PostMapping("login/process")
    public String loginAuthentication(@Validated @ModelAttribute LoginForm loginForm, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        if (memberService.validLogin(loginForm)) {
            return "redirect:/";
        }
        else{
            return "error";
        }
    }
}
