package kfq.springcoco.controller;

import kfq.springcoco.entity.*;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final CustomUserDetailsService customUserDetailsService;
    private final SkillService skillService;

    // 멤버에 스킬 추가
    @PostMapping("/api/skills")
    public ResponseEntity<String> addSkill(String skill, String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else if (skillService.skillMember(id).contains(skill)) {
            res = new ResponseEntity<String>("이미 해당 기술을 추가하셨습니다", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                skillService.addSkill(SkillEnum.valueOf(skill), member);
                res = new ResponseEntity<String>("기술 추가 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("기술 추가 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    // 멤버의 기술 리스트 : 멤버가 설정한 기술 리스트
    @GetMapping("/api/skills")
    public ResponseEntity<List<Skill>> skillByMember(String id) throws Exception {
        ResponseEntity<List<Skill>> res = null;
        List<Skill> skills = null;
        try {
            Member member = (Member) customUserDetailsService.loadUserByUsername(id);
            skills = member.getSkillList();
            res = new ResponseEntity<List<Skill>>(skills, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Skill>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 기술 Enum 리스트 보내기
    @GetMapping("/api/skills/list")
    public ResponseEntity<List<String>> skillList() {
        ResponseEntity<List<String>> res = null;
        List<String> skills = null;
        skills = skillService.skillEnum();
        res = new ResponseEntity<List<String>>(skills, HttpStatus.OK);
        return res;
    }
}
