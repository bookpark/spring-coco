package kfq.springcoco.controller;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Recommend;
import kfq.springcoco.service.AnswerService;
import kfq.springcoco.service.CustomUserDetailsService;
import kfq.springcoco.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;
    private final AnswerService answerService;
    private final CustomUserDetailsService customUserDetailsService;

    // 추천하기
    @PostMapping("/api/recommends/{answerId}")
    public ResponseEntity<String> recommendAnswer(@PathVariable Integer answerId,
                                                  String id) {
        ResponseEntity<String> res = null;
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        if (id == null || id.equals("")) {
            res = new ResponseEntity<String>("로그인 필요", HttpStatus.BAD_REQUEST);
        } else if (recommendService.memberInRecommend(answerId).contains(id)) {
            Recommend recommend = recommendService.getRecommend(member);
            recommendService.deleteRecommend(recommend);
            res = new ResponseEntity<String>("추천을 취소하였습니다", HttpStatus.OK);
        } else {
            try {
                Answer answer = answerService.getAnswer(answerId);
                recommendService.recommendAnswer(answer, member);
                res = new ResponseEntity<String>("추천 성공", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    @GetMapping("/api/recommends/{answerId}")
    public ResponseEntity<List<Recommend>> recommendAnswerList(@PathVariable Integer answerId,
                                                               String id) {
        ResponseEntity<List<Recommend>> res = null;
        try {
            Answer answer = answerService.getAnswer(answerId);
            List<Recommend> recommends = answer.getRecommendList();
            res = new ResponseEntity<List<Recommend>>(recommends, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Recommend>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}
