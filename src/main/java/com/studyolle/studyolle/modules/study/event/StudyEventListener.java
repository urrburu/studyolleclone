package com.studyolle.studyolle.modules.study.event;

import com.studyolle.studyolle.modules.study.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class StudyEventListener {


    @EventListener
    public void handlerStudyCreatedEvent(StudyCreatedEvent studyCreatedEvent){
        Study study = studyCreatedEvent.getStudy();
        log.info(study.getTitle() + "is created.");
    }

}
