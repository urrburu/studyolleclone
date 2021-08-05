package com.studyolle.studyolle.modules.study;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StudyRepositoryExtensionImpl extends QuerydslRepositorySupport implements StudyRepositoryExtension {
    public StudyRepositoryExtensionImpl(Class<?> domainClass) {
        super(domainClass);
    }//구현체를 만뜰때는 뒤에 반드시 Impl붙일것
    @Override
    public List<Study> findByKeyword(String keyword){
        return null;

    }
}
