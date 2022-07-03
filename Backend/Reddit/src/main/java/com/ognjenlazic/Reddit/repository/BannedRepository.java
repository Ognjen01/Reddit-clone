package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedRepository extends JpaRepository<Banned, Long> {
}