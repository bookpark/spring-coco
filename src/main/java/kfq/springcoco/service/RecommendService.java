package kfq.springcoco.service;

import kfq.springcoco.entity.Answer;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Recommend;
import kfq.springcoco.repository.AnswerRepository;
import kfq.springcoco.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final AnswerRepository answerRepository;

    // 추천 기능
    public void recommendAnswer(Answer answer, Member member) {
        Recommend recommend = new Recommend();
        recommend.setAnswer(answer);
        recommend.setMember(member);
        recommendRepository.save(recommend);
    }

    // 추천 삭제
    public void deleteRecommend(Recommend recommend) {
        recommendRepository.delete(recommend);
    }

    // 추천 객체 반환
    public Recommend getRecommend(Member member) {
        return recommendRepository.findByMember(member);
    }

    // 추천 목록에 있는 멤버의 이메일 반환
    public List<String> memberInRecommend(Integer answerId) {
        Optional<Answer> byAnswer = answerRepository.findById(answerId);
        assert byAnswer.orElse(null) != null;
        List<Recommend> recommendList = byAnswer.orElse(null).getRecommendList();
        List<String> collect = recommendList.stream()
                .flatMap(member -> {
                    List<String> memberList = new ArrayList<>();
                    memberList.add(member.getMember().getEmail());
                    return memberList.stream();
                })
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }
}
