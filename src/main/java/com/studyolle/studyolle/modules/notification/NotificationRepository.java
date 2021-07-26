package com.studyolle.studyolle.modules.notification;

import com.studyolle.studyolle.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByAccountAndChecked(Account account, boolean checked);
}
