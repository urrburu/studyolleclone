package com.studyolle.studyolle.event;

import com.studyolle.studyolle.domain.Account;
import com.studyolle.studyolle.domain.Enrollment;
import com.studyolle.studyolle.domain.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository <Enrollment, Long> {
    boolean existsByEventAndAccount(Event event, Account account);


    Enrollment findByEventAndAccount(Event event, Account account);

    @EntityGraph("Enrollment.withEventAndStudy")
    List<Enrollment> findByAccountAndAcceptedOrderByEnrolledAtDesc(Account account, boolean accepted);
}
