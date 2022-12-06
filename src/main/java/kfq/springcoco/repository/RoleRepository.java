package kfq.springcoco.repository;

import kfq.springcoco.domain.ERole;
import kfq.springcoco.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
