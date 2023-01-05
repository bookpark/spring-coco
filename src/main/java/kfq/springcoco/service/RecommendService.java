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
    private final AnswerService answerService;

    // 추천 기능
    public void recommendAnswer(Answer answer, Member member) {
        Recommend recommend = new Recommend();
        recommend.setAnswer(answer);
        recommend.setMember(member);
        recommendRepository.save(recommend);
    }

    // 추천 삭제
    public void deleteRecommend(Recommend recommend) {
        recommendRepository.deleteById(recommend.getRecommendId());
    }

    // 추천 객체 반환
    public Recommend getRecommend(Answer answer, Member member) {
        return recommendRepository.findByAnswerAndMember(answer, member);
    }

    // 추천 목록에 있는 멤버의 이메일 반환
    public List<String> memberInRecommend(Integer answerId) throws Exception {
        Optional<Answer> byAnswer = Optional.ofNullable(answerService.getAnswer(answerId));
        assert byAnswer.orElse(null) != null;
        List<Recommend> recommendList = byAnswer.orElse(null).getRecommendList();
        return recommendList.stream()
                .flatMap(member -> {
                    List<String> memberList = new ArrayList<>();
                    memberList.add(member.getMember().getEmail());
                    return memberList.stream();
                })
                .collect(Collectors.toList());
    }

    public List<Recommend> recommendList() {
        return recommendRepository.findAll();
    }

    public Integer recommendCount(Integer answerId) {
        return recommendRepository.recommendCount(answerId);
    }
}
