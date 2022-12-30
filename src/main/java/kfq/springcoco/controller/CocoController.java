package kfq.springcoco.controller;

import kfq.springcoco.dto.CocoDTO;
import kfq.springcoco.entity.Coco;
import kfq.springcoco.entity.CocoStatus;
import kfq.springcoco.entity.Member;
import kfq.springcoco.repository.CocoRepository;
import kfq.springcoco.service.CocoService;
import kfq.springcoco.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CocoController {

    private final CocoService cocoService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CocoRepository cocoRepository;

    // 코코 등록
    @PostMapping("/api/cocos")
    public ResponseEntity<String> createCoco(@Valid CocoDTO cocoDTO,
                                             String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                Coco c = new Coco();
                c.setTitle(cocoDTO.getTitle());
                c.setContent(cocoDTO.getContent());
                c.setPrice(cocoDTO.getPrice());
                c.setLanguageList(cocoDTO.getLanguageList());
                c.setSkillList(cocoDTO.getSkillList());
                c.setCreatedTime(LocalDateTime.now());
                c.setAuthor(member);
                c.setStatus(CocoStatus.WAITING);
                cocoRepository.save(c);
                res = new ResponseEntity<String>("코코 등록 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>("코코 등록 실패", HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    // 코코 전체 리스트
    @GetMapping("/api/cocos")
    public ResponseEntity<List<Coco>> cocoList() throws Exception {
        ResponseEntity<List<Coco>> res = null;
        List<Coco> cocos = null;
        try {
            cocos = cocoService.cocoList();
            res = new ResponseEntity<List<Coco>>(cocos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Coco>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
