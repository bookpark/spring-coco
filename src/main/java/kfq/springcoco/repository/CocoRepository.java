package kfq.springcoco.repository;

import kfq.springcoco.entity.Coco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocoRepository extends JpaRepository<Coco, Integer> {
}
