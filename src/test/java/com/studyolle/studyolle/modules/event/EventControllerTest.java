package com.studyolle.studyolle.modules.event;

import com.studyolle.studyolle.infra.MockMvcTest;
import com.studyolle.studyolle.modules.account.AccountRepository;
import com.studyolle.studyolle.modules.study.StudyRepository;
import com.studyolle.studyolle.modules.study.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudyService studyService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    AccountRepository accountRepository;

}
