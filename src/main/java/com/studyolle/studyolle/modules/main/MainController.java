package com.studyolle.studyolle.modules.main;

import com.studyolle.studyolle.modules.account.CurrentUser;
import com.studyolle.studyolle.modules.account.Account;
import com.studyolle.studyolle.modules.notification.NotificationRepository;
import com.studyolle.studyolle.modules.study.Study;
import com.studyolle.studyolle.modules.study.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NotificationRepository notificationRepository;
    private final StudyRepository studyRepository;


    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model)
    {
        if(account != null){
            model.addAttribute(account);
        }

        long count = notificationRepository.countByAccountAndChecked(account,false);
        model.addAttribute("hasNotification", count>0);
        return "index";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }
    @GetMapping("/search/study")
    public String searchStudy(@PageableDefault(size =9, sort="publishedDateTime", direction = Sort.Direction.ASC) Pageable pageable, String keyword, Model model){
        Page<Study> studyPage = studyRepository.findByKeyword(keyword, pageable);
        model.addAttribute("studyPage", studyPage);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
