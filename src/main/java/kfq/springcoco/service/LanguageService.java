package kfq.springcoco.service;

import kfq.springcoco.entity.Language;
import kfq.springcoco.entity.LanguageEnum;
import kfq.springcoco.entity.Member;
import kfq.springcoco.repository.LanguageRepository;
import kfq.springcoco.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;


    public Language findLanguage(Integer languageId) throws Exception {
        Optional<Language> oLanguage = languageRepository.findById(languageId);
        if (oLanguage.isPresent()) {
            return oLanguage.get();
        }
        throw new Exception("언어 찾지 못함");
    }

    // 언어 리스트
    public List<Language> languageList() {
        return this.languageRepository.findAll(Sort.by(Sort.Direction.ASC, "languageId"));
    }

    // Language Enum값 리스트로 뽑아내기
    public List<String> languageEnums() {
        LanguageEnum[] languageEnum = LanguageEnum.values();
        return Arrays.stream(languageEnum)
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // 멤버에 언어 추가
    public void addLanguage(LanguageEnum language, Member member) {
        Language lang = new Language();
        lang.setLanguage(language);
        lang.setMember(member);
        languageRepository.save(lang);
    }

}
