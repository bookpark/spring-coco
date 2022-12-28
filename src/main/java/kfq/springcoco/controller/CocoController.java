package kfq.springcoco.controller;

import kfq.springcoco.entity.Coco;
import kfq.springcoco.entity.Member;
import kfq.springcoco.service.CocoService;
import kfq.springcoco.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CocoController {

    private final CocoService cocoService;
    private final CustomUserDetailsService customUserDetailsService;

    // 코코 등록
    @PostMapping("/api/cocos")
    public ResponseEntity<String> createCoco(@RequestParam String title,
                                             @RequestParam String content,
                                             @RequestParam Integer price,
                                             @RequestParam List<String> languageList,
                                             @RequestParam List<String> skillList,
                                             @RequestParam String id) {
        ResponseEntity<String> res = null;
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else {
            try {
                Member member = (Member) customUserDetailsService.loadUserByUsername(id);
                cocoService.createCoco(title, content, price, languageList, skillList, member);
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
