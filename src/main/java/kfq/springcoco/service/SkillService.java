package kfq.springcoco.service;

import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Skill;
import kfq.springcoco.entity.SkillEnum;
import kfq.springcoco.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    // Skill Enum값 리스트로 뽑아내기
    public List<String> skillEnum() {
        SkillEnum[] skillEnum = SkillEnum.values();
        return Arrays.stream(skillEnum)
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // 멤버에 스킬 추가
    public void addSkill(SkillEnum skill, Member member) {
        Skill s = new Skill();
        s.setSkill(skill);
        s.setMember(member);
        skillRepository.save(s);
    }

}
