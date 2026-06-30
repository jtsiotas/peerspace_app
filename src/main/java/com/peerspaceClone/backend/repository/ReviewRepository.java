package com.peerspaceClone.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.peerspaceClone.backend.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookingId(Long bookingId);
    List<Review> findByRevieweeId(Long revieweeId);
}
