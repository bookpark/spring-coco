package kfq.springcoco.service;

import kfq.springcoco.entity.Language;
import kfq.springcoco.entity.LanguageEnum;
import kfq.springcoco.entity.Member;
import kfq.springcoco.repository.LanguageRepository;
import kfq.springcoco.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final MemberRepository memberRepository;


    public Language findLanguage(Integer languageId) throws Exception {
        Optional<Language> oLanguage = languageRepository.findById(languageId);
        if (oLanguage.isPresent()) {
            return oLanguage.get();
        }
        throw new Exception("언어 찾지 못함");
    }

    public String findByLanguage(String language) {
        return languageRepository.findByLanguage(LanguageEnum.valueOf(language)).toString();
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

    // 멤버가 등록한 언어들을 List로 변환함
    public List<String> languageMember(String id) {
        Optional<Member> byEmail = memberRepository.findByEmail(id);
        assert byEmail.orElse(null) != null;
        List<Language> languageList = byEmail.orElse(null).getLanguageList();
        return languageList.stream()
                .flatMap(language -> {
                    List<String> languageValues = new ArrayList<>();
                    languageValues.add(language.getLanguage().toString());
                    return languageValues.stream();
                })
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

