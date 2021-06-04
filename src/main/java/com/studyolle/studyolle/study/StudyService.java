package com.studyolle.studyolle.study;

import com.studyolle.studyolle.domain.Account;
import com.studyolle.studyolle.domain.Study;
import com.studyolle.studyolle.domain.Tag;
import com.studyolle.studyolle.domain.Zone;
import com.studyolle.studyolle.study.event.StudyCreatedEvent;
import com.studyolle.studyolle.study.event.StudyUpdateEvent;
import com.studyolle.studyolle.study.form.StudyDescriptionForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {

    private final StudyRepository repository;
    private final ModelMapper modelMapper;
    private ApplicationEventPublisher eventPublisher;

    public Study createNewStudy(Study study, Account account) {
        Study newStudy = repository.save(study);
        newStudy.addManager(account);
        return newStudy;
    }

    public Study getStudyToUpdate(Account account, String path)  {
        Study study = this.getStudy(path);
        if(!account.isManagerOf(study)){
            throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
        }
        return study;
    }

    public Study getStudy(String path){
        Study study = this.repository.findByPath(path);
        if(study==null){
            throw new IllegalArgumentException(path+"에 해당하는 스터디가 없습니다.");
        }
        return study;
    }
    public void updateStudyDescription(Study study, StudyDescriptionForm studyDescriptionForm){
        modelMapper.map(studyDescriptionForm, study);
    }

    public void updateStudyImage(Study study, String image) {
        study.setImage(image);
    }

    public void enableStudyBanner(Study study) {
        study.setUseBanner(true);
    }

    public void disableStudyBanner(Study study) {
        study.setUseBanner(false);
    }

    public void addTag(Study study, Tag tag) { study.getTags().add(tag); }
    public void removeTag(Study study, Tag tag) { study.getTags().remove(tag); }
    public void addZone(Study study, Zone zone){study.getZones().add(zone);}
    public void removeZone(Study study, Zone zone){study.getZones().remove(zone);}
    
    public Study getStudyToUpdateTag(Account account, String path){
        Study study = repository.findStudyWithTagsByPath(path);
        checkIfExistingStudy(path, study);
        checkIfManager(account, study);
        return study;
    }
    public Study getStudyToUpdateZone(Account account, String path) {
        Study study = repository.findStudyWithZonesByPath(path);
        checkIfExistingStudy(path, study);
        checkIfManager(account, study);
        return study;
    }

    private void checkIfManager(Account account, Study study) {
        if(!study.isManagedBy(account)){
            throw new AccessDeniedException("해당기능을 사용할 수 없습니다.");
        }
    }

    private void checkIfExistingStudy(String path, Study study) {
        if (study == null) {
            throw new IllegalArgumentException(path + "에 해당하는 스터디가 없습니다.");
        }
    }


    public Study getStudyToUpdateStatus(Account account, String path) {
        Study study = repository.findStudyWithManagersByPath(path);
        checkIfExistingStudy(path, study);
        checkIfManager(account, study);
        return study;
    }

    public void publish(Study study) {
        study.publish();
        //this.eventPublisher.publishEvent(new StudyCreatedEvent(study));
    }

    public void close(Study study) {
        study.close();
        //this.eventPublisher.publishEvent(new StudyCreatedEvent(study));
    }

    public void startRecruit(Study study) {
        study.startRecruit();
    }
    public void stopRecruit(Study study){
        study.stopRecruit();
    }
}
