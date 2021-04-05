package com.studyolle.studyolle.account;

import com.studyolle.studyolle.domain.Account;
import com.studyolle.studyolle.settings.SettingsController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SettingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    void afterEach(){
        accountRepository.deleteAll();

    }

    @WithAccount("chanhwi")
    @DisplayName("Edit profile")
    @Test
    void updateProfile()throws Exception{
        String bio = "짧은 소개를 수정하는 경우" ;
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                .param("bio", bio)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(flash().attributeExists("message"));
        Account chanhwi = accountRepository.findByNickname("chanhwi");
        assertEquals(bio, chanhwi.getBio());

    }

    @WithAccount("chanhwi")
    @DisplayName("Edit profile - with Error")
    @Test
    void updateWithErrorProfile()throws Exception{
        String bio = "짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우짧은 소개를 수정하는 경우" ;
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                .param("bio", bio)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PROFILE_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account chanhwi = accountRepository.findByNickname("chanhwi");
        assertNull(chanhwi.getBio());

    }

    @WithAccount("chanhwi")
    @DisplayName("profile edit form")
    @Test
    void updateProfileForm() throws Exception{
        String bio = "짧은 소개를 수정하는 경우";
        mockMvc.perform(get(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));

    }
}
