package kfq.springcoco.repository;

import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Member> {
}
