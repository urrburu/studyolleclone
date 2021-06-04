package com.studyolle.studyolle.study.event;

import com.studyolle.studyolle.domain.Study;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter@RequiredArgsConstructor
public class StudyCreatedEvent {
    private final Study study;
}
