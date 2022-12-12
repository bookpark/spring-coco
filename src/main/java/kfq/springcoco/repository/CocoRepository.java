package kfq.springcoco.repository;

import kfq.springcoco.domain.Coco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocoRepository extends JpaRepository<Coco, Integer> {
}
