package com.studyolle.studyolle.modules.study;

import com.studyolle.studyolle.modules.account.Account;
import com.studyolle.studyolle.modules.study.event.StudyCreatedEvent;
import com.studyolle.studyolle.modules.study.event.StudyUpdateEvent;
import com.studyolle.studyolle.modules.tag.Tag;
import com.studyolle.studyolle.modules.tag.TagRepository;
import com.studyolle.studyolle.modules.zone.Zone;
import com.studyolle.studyolle.modules.study.form.StudyDescriptionForm;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static com.studyolle.studyolle.modules.study.form.StudyForm.VALID_PATH_PATTERN;


@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {

    private final StudyRepository repository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final TagRepository tagRepository;

    public Study createNewStudy(Study study, Account account) {
        Study newStudy = repository.save(study);
        newStudy.addManager(account);
        return newStudy;
    }

    public Study getStudyToUpdate(Account account, String path)  {
        Study study = this.getStudy(path);
        if(!study.isManagedBy(account)){
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
        eventPublisher.publishEvent(new StudyUpdateEvent(study, " 스터디 소개를 수정했습니다. "));
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
        this.eventPublisher.publishEvent(new StudyCreatedEvent(study));
    }

    public void close(Study study) {
        study.close();
        eventPublisher.publishEvent(new StudyUpdateEvent(study, "스터디를 종료했습니다. "));
    }

    public void startRecruit(Study study) {
        study.startRecruit();
        eventPublisher.publishEvent(new StudyUpdateEvent(study, "팀원 모집을 시작합니다."));
    }
    public void stopRecruit(Study study){
        study.stopRecruit();
        eventPublisher.publishEvent(new StudyUpdateEvent(study, "팀원 모집을 종료했습니다."));
    }

    public boolean isValidPath(String newPath) {
        if(!newPath.matches(VALID_PATH_PATTERN)){
            return false;
        }
        return !repository.existsByPath(newPath);
    }

    public void updateStudyPath(Study study, String newPath) {
        study.setPath(newPath);
    }
    public boolean isValidTitle(String newTitle) {
        return newTitle.length() <= 50;
    }
    public void updateStudyTitle(Study study, String newTitle) {
        study.setTitle(newTitle);
    }

    public void remove(Study study) {
        if(study.isRemovable()){
            repository.delete(study);
        }
        else{
            throw new IllegalArgumentException("스터디를 삭제할 수 없습니다.");
        }
    }

    public void addMember(Study study, Account account) {
        study.addMember(account);
    }

    public void removeMember(Study study, Account account) {
        study.removeMember(account);
    }

    public Study getStudyToEnroll(String path) {
        Study study = repository.findStudyOnlyByPath(path);
        checkIfExistingStudy(path, study);
        return study;
    }


    public void generateTestStudies(Account account) {
        for(int i=0;i<30;++i){
            String randomvalue = RandomString.make(5);
            Study st = Study.builder()
                    .title("테스트 스터디 "+ randomvalue)
                    .path("test-"+randomvalue)
                    .shortDescription("테스트용 스터디")
                    .fullDescription("test")
                    .tags(new HashSet<>())
                    .managers(new HashSet<>())
                    .build();
            st.publish();
            Study newStudy = this.createNewStudy(st, account);
            Tag spring = tagRepository.findByTitle("spring");
            newStudy.getTags().add(spring);
        }
    }
}
