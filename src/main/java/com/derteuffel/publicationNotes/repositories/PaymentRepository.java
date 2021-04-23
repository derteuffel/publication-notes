package com.derteuffel.publicationNotes.repositories;

import com.derteuffel.publicationNotes.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByUserInfo_Id(Long id);

    Payment findByUserInfo_IdAndCode(Long id, String code);
    Payment findByUserInfo_IdAndPeriodeAndNiveau(Long id, String periode, String niveau);
}
