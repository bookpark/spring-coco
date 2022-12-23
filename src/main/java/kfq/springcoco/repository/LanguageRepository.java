package kfq.springcoco.repository;

import kfq.springcoco.entity.Language;
import kfq.springcoco.entity.LanguageEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Optional<Language> findByLanguage(LanguageEnum language);
}
