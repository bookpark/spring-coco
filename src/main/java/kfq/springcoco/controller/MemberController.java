package kfq.springcoco.controller;

import kfq.springcoco.domain.ERole;
import kfq.springcoco.domain.Member;
import kfq.springcoco.domain.Role;
import kfq.springcoco.dto.JwtResponse;
import kfq.springcoco.dto.LoginDto;
import kfq.springcoco.dto.MessageResponse;
import kfq.springcoco.dto.SignUpDto;
import kfq.springcoco.repository.MemberRepository;
import kfq.springcoco.repository.RoleRepository;
import kfq.springcoco.security.UserDetailsImpl;
import kfq.springcoco.security.jwt.JwtUtils;
import kfq.springcoco.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getMember_id(),
                userDetails.getNickname(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/api/members")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
//        Member member = new Member(signUpDto.getEmail(),
//                signUpDto.getNickname(),
//                encoder.encode(signUpDto.getPassword()));
        Member member = new Member();
        member.setNickname(signUpDto.getNickname());
        member.setEmail(signUpDto.getEmail());
        member.setPassword(encoder.encode(signUpDto.getPassword()));

//        Set<String> strRoles = signUpDto.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                if (role.equals("admin")) {
//                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(adminRole);
//                } else {
//                    Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(userRole);
//                }
//            });
//        }
//
//        member.setRoles(roles);
        memberRepository.save(member);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @PostMapping("/api/members")
//    public ResponseEntity<String> signUp(@RequestParam("email") String email,
//                                         @RequestParam("password") String password,
//                                         @RequestParam("nickname") String nickname
//    ) {
//        ResponseEntity<String> res = null;
//        try {
//            memberService.signUp(email, password, nickname);
//            res = new ResponseEntity<String>("회원가입 성공", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res = new ResponseEntity<String>("회원가입 실패", HttpStatus.BAD_REQUEST);
//        }
//        return res;
//    }

}
