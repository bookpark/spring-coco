package kfq.springcoco.controller;

import kfq.springcoco.entity.Member;
import kfq.springcoco.payload.request.SignupRequest;
import kfq.springcoco.payload.response.MessageResponse;
import kfq.springcoco.repository.MemberRepository;
import kfq.springcoco.security.jwt.JwtTokenProvider;
import kfq.springcoco.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @PostMapping("/test")
    public ResponseEntity<?> test(String email, String password) {
        System.out.println(email);
        System.out.println(password);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email,
                                                     String password) {
        System.out.println(email);
        System.out.println(password);
        try {
            Map<String, String> res = new HashMap<>();
            Member member = (Member) customUserDetailsService.loadUserByUsername(email);
            if (member != null && passwordEncoder.matches(password, member.getPassword())) {
                String accessToken = jwtTokenProvider.createToken(member.getUsername());
                String refreshToken = jwtTokenProvider.refreshToken(member.getUsername());
                res.put("email", member.getEmail());
                res.put("nickname", member.getNickname());
                res.put("accessToken", accessToken);
                res.put("refreshToken", refreshToken);
                return new ResponseEntity<Map<String, String>>(res, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, String>>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/members")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 사용된 이메일 주소입니다."));
        }

        if (memberRepository.existsByNickname(signupRequest.getNickname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 사용된 닉네임입니다."));
        }

        Member member = new Member();
        member.setNickname(signupRequest.getNickname());
        member.setEmail(signupRequest.getEmail());
        member.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        memberRepository.save(member);
        return ResponseEntity.ok(new MessageResponse("회원가입 성공!"));
    }

}
