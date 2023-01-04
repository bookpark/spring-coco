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
    public ResponseEntity<Long> recommendAnswer(@PathVariable Integer answerId,
                                                  String id) throws Exception {
        ResponseEntity<Long> res = null;
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        Answer answer = answerService.getAnswer(answerId);
        long recommends = (long) answer.getRecommendList().size();
        if (id == null || id.equals("")) {
            res = new ResponseEntity<Long>(recommends, HttpStatus.BAD_REQUEST);
        } else if (recommendService.memberInRecommend(answerId).contains(id)) {
            Recommend recommend = recommendService.getRecommend(answer, member);
            recommendService.deleteRecommend(recommend);
            res = new ResponseEntity<Long>(recommends, HttpStatus.OK);
        } else {
            try {
                recommendService.recommendAnswer(answer, member);
                res = new ResponseEntity<Long>(recommends, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
            }
            return res;
        }
        return res;
    }

    @GetMapping("/api/recommends")
    public ResponseEntity<List<Recommend>> recommendAnswerList() {
        ResponseEntity<List<Recommend>> res = null;
        try {
            List<Recommend> recommends = recommendService.recommendList();
            System.out.println(recommends);
            res = new ResponseEntity<List<Recommend>>(recommends, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Recommend>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}
