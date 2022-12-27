package kfq.springcoco.service;

import kfq.springcoco.entity.Coco;
import kfq.springcoco.entity.CocoStatus;
import kfq.springcoco.entity.Member;
import kfq.springcoco.entity.Question;
import kfq.springcoco.repository.CocoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CocoService {

    private final CocoRepository cocoRepository;

    public void createCoco(String title, String content, Integer price,
                           List<Object> languageList,
                           List<Object> skillList,
                           Member author) {
        Coco coco = new Coco();
        coco.setTitle(title);
        coco.setContent(content);
        coco.setPrice(price);
        coco.setCreatedTime(LocalDateTime.now());
        coco.setLanguageList(languageList);
        coco.setSkillList(skillList);
        coco.setAuthor(author);
        coco.setStatus(CocoStatus.WAITING);
        this.cocoRepository.save(coco);
    }

    public List<Coco> cocoList() {
        return this.cocoRepository.findAll(Sort.by(Sort.Direction.DESC, "cocoId"));
    }

}
