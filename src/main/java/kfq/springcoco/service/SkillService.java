package kfq.springcoco.service;

import kfq.springcoco.entity.Language;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Skill;
import kfq.springcoco.entity.SkillEnum;
import kfq.springcoco.repository.MemberRepository;
import kfq.springcoco.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final MemberRepository memberRepository;

    // Skill Enum값 리스트로 뽑아내기
    public List<String> skillEnum() {
        SkillEnum[] skillEnum = SkillEnum.values();
        return Arrays.stream(skillEnum)
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // 멤버가 등록한 Skill들을 List로 변환함
    public List<String> skillMember(String id) {
        Optional<Member> byEmail = memberRepository.findByEmail(id);
        assert byEmail.orElse(null) != null;
        List<Skill> skillList = byEmail.orElse(null).getSkillList();
        return skillList.stream()
                .flatMap(skill -> {
                    List<String> languageValues = new ArrayList<>();
                    languageValues.add(skill.getSkill().toString());
                    return languageValues.stream();
                })
                .collect(Collectors.toList());
    }

    // 멤버에 스킬 추가
    public void addSkill(SkillEnum skill, Member member) {
        Skill s = new Skill();
        s.setSkill(skill);
        s.setMember(member);
        skillRepository.save(s);
    }

    // 멤버의 스킬 삭제
    public void deleteSkill(Skill skill) {
        this.skillRepository.delete(skill);
    }

}
