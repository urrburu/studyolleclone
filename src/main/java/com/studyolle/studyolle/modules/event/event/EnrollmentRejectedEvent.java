package com.studyolle.studyolle.modules.event.event;

import com.studyolle.studyolle.modules.event.Enrollment;

public class EnrollmentRejectedEvent extends EnrollmentEvent{
    public EnrollmentRejectedEvent(Enrollment enrollment){
        super(enrollment, "모임참가신청이 거절되었습니다. ");
    }
}
