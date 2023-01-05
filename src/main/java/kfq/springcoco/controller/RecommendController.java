package kfq.springcoco.controller;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Recommend;
import kfq.springcoco.repository.AnswerRepository;
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
    private final AnswerRepository answerRepository;

    // 추천하기
    @PostMapping("/api/recommends/{answerId}")
    public ResponseEntity<Integer> recommendAnswer(@PathVariable Integer answerId,
                                                  String id) throws Exception {
        ResponseEntity<Integer> res = null;
        Member member = (Member) customUserDetailsService.loadUserByUsername(id);
        Answer answer = answerService.getAnswer(answerId);
//        int size = recommendService.recommendCount(answerId);
//        answer.setRecommendCount(size);
//        answerRepository.save(answer);
        if (id == null || id.equals("")) {
            res = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        } else if (recommendService.memberInRecommend(answerId).contains(id)) {
            Recommend recommend = recommendService.getRecommend(answer, member);
            recommendService.deleteRecommend(recommend);
            int sizeAfterDelete = recommendService.recommendCount(answerId);
            answer.setRecommendCount(sizeAfterDelete);
            System.out.println(answer);
            answerRepository.updateRecommendCount(sizeAfterDelete, answerId);
            System.out.println("추천취소함: " + sizeAfterDelete);
            res = new ResponseEntity<Integer>(sizeAfterDelete, HttpStatus.OK);
        } else {
            try {
                recommendService.recommendAnswer(answer, member);
                int sizeAfterRecommend = recommendService.recommendCount(answerId);
                answer.setRecommendCount(sizeAfterRecommend);
                answerRepository.updateRecommendCount(sizeAfterRecommend, answerId);
                System.out.println("추천함: " + sizeAfterRecommend);
                res = new ResponseEntity<Integer>(sizeAfterRecommend, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                res = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
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
