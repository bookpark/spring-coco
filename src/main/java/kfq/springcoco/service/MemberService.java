package kfq.springcoco.service;

import kfq.springcoco.domain.Member;
import kfq.springcoco.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signUp(String nickname, String email, String password) {
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }

}
