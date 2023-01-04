package kfq.springcoco.service;

import kfq.springcoco.entity.Member;
import kfq.springcoco.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    public void saveImage(Member member, MultipartFile file) throws Exception {
        String filename = null;
        if (file != null && !file.isEmpty()) {
//            String path = "/Users/book/KFQ/final/uploads/";
            String path = "/Users/ebina/Coco/test-img/";
//            String path = "/Users/bang/KFQ/project/img/";
            filename = file.getOriginalFilename();
            File dFile = new File(path + filename);
            file.transferTo(dFile);
            member.setFilename(filename);
        }
        memberRepository.save(member);
    }

    /**
     * 멤버 삭제/탈퇴
     * @param memberId
     * @throws Exception
     */
    public void deleteMember(Integer memberId) throws Exception {
            this.memberRepository.deleteById(memberId);
    }

}
