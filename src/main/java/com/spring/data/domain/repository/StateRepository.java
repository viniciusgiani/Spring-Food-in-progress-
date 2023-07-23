package com.spring.data.domain.repository;

import com.spring.data.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
