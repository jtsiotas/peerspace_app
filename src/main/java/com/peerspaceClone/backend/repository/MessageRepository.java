package com.peerspaceClone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.peerspaceClone.backend.model.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByBookingId(Long bookingId);
}
