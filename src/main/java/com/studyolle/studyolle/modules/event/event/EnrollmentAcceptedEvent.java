package com.studyolle.studyolle.modules.event.event;

import com.studyolle.studyolle.modules.event.Enrollment;

public class EnrollmentAcceptedEvent extends EnrollmentEvent{
    public EnrollmentAcceptedEvent(Enrollment enrollment){
        super(enrollment, "모임참가신청을 확인했습니다. 모임에 참석하세요.");
    }
}
