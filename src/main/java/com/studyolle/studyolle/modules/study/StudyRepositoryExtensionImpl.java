package com.studyolle.studyolle.modules.study;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.studyolle.studyolle.modules.account.QAccount;
import com.studyolle.studyolle.modules.tag.QTag;
import com.studyolle.studyolle.modules.zone.QZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StudyRepositoryExtensionImpl extends QuerydslRepositorySupport implements StudyRepositoryExtension {
    public StudyRepositoryExtensionImpl() {
        super(Study.class);
    }//구현체를 만뜰때는 뒤에 반드시 Impl붙일것
    //상위 클래스에 기본 생성자가 없고 패러미터르 ㄹ하나 받는 생성자 밖에 없기때문에
    //기본생성자만 갖고 있으면 안되므로 자식클래스의 생성자를 만들어줍니다.
    @Override
    public Page<Study> findByKeyword(String keyword, Pageable pageable){
        QStudy study = QStudy.study;
        JPQLQuery<Study> query =from(study).where(study.published.isTrue()
                .and(study.title.containsIgnoreCase(keyword))
                .or(study.tags.any().title.containsIgnoreCase(keyword))
                .or(study.zones.any().localNameOfCity.containsIgnoreCase(keyword)))
                .leftJoin(study.tags, QTag.tag).fetchJoin()
                .leftJoin(study.zones, QZone.zone).fetchJoin()
                .leftJoin(study.members, QAccount.account).fetchJoin()
                .distinct();
        JPQLQuery<Study> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Study> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());

    }
}
