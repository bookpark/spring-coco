package kfq.springcoco.controller;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.payload.request.SignupRequest;
import kfq.springcoco.payload.request.UpdateRequest;
import kfq.springcoco.payload.response.MessageResponse;
import kfq.springcoco.repository.MemberRepository;
import kfq.springcoco.security.jwt.JwtTokenProvider;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
public class MemberController {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

//    @ExceptionHandler(IllegalArgumentException.class)
//    public String handleException() {
//        return "IllegalArgumentException!";
//    }

    /**
     * 로그인
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(String email, String password) {
        try {
            Map<String, String> res = new HashMap<>();
            Member member = (Member) customUserDetailsService.loadUserByUsername(email);
            if (member != null && passwordEncoder.matches(password, member.getPassword())) {
                String accessToken = jwtTokenProvider.createToken(member.getUsername());
                String refreshToken = jwtTokenProvider.refreshToken(member.getUsername());
                System.out.println(accessToken + "," + refreshToken);
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

    /**
     * 회원가입
     *
     * @param signupRequest
     * @return
     */
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

    // 프로필 조회
    @PostMapping("/api/members/profile")
    public ResponseEntity<Member> retrieveUser(String id) {
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    // 닉네임 변경
    @PutMapping("/api/members/profile")
    public ResponseEntity<?> updateUser(UpdateRequest updateRequest,
                                        String id) {
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        if (memberRepository.existsByNickname(updateRequest.getNickname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 사용된 닉네임입니다."));
        }
        member.setNickname(updateRequest.getNickname());
        memberRepository.save(member);
        return ResponseEntity.ok(new MessageResponse("닉네임이 성공적으로 수정되었습니다."));
    }

    /**
     * 회원탈퇴
     *
     * @param id
     * @return
     */
    @PostMapping("/api/members/delete")
    public ResponseEntity<?> deleteUser(String id) {
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        if (!memberRepository.existsByEmail(id)) {
            return ResponseEntity
                    .badRequest().body(new MessageResponse("존재하지 않는 회원입니다."));
        }
        try {
            memberService.deleteMember(member.getMemberId());
            return ResponseEntity.ok(new MessageResponse("탈퇴 처리되었습니다."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .badRequest().body(new MessageResponse("탈퇴 처리 실패"));
    }

    // 멤버별 질문 리스트
    @GetMapping("/api/members/questions")
    public ResponseEntity<List<Question>> memberQuestionList(String id) throws Exception {
        ResponseEntity<List<Question>> res = null;
        List<Question> questions = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            questions = member.getQuestionList();
            res = new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 멤버별 답변 리스트
    @GetMapping("/api/members/answers")
    public ResponseEntity<List<Answer>> memberAnswerList(String id) throws Exception {
        ResponseEntity<List<Answer>> res = null;
        List<Answer> answers = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            answers = member.getAnswerList();
            res = new ResponseEntity<List<Answer>>(answers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Answer>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 프로필 이미지 업로드
    @PostMapping("/api/members/profile/image")
    public ResponseEntity<String> saveImage(String id,
                                            MultipartFile file) {
        ResponseEntity<String> res = null;
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        try {
            memberService.saveImage(member, file);
            res = new ResponseEntity<String>("프로필 이미지 업로드 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("프로필 이미지 업로드 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 이미지 조회
    @GetMapping("/img/{filename}")
    public void imageView(@PathVariable String filename, HttpServletResponse response) {
        System.out.println(filename);
        try {
            String path = "/Users/bang/KFQ/project/img/";
            FileInputStream fis = new FileInputStream(path + filename);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(fis, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
