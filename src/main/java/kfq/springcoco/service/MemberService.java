package kfq.springcoco.service;

import kfq.springcoco.domain.Member;
import kfq.springcoco.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Member findMember(Integer memberId) throws Exception {
        Optional<Member> member = this.memberRepository.findById(memberId);
        if (member.isPresent()) {
            return member.get();
        }
        throw new Exception("조회 실패!");
    }

    public void deleteMember(Integer memberId) throws Exception {
        try {
            this.memberRepository.deleteById(memberId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
