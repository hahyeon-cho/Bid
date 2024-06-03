package com.kcs3.bid.repository;

import com.kcs3.bid.entity.Alarm;
import com.kcs3.bid.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findTop4ByUserOrderByCreatedAtDesc(User user);

}
