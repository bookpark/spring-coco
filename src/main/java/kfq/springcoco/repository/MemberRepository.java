package kfq.springcoco.repository;

import kfq.springcoco.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNicknameOrEmail(String nickname, String email);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);
}
