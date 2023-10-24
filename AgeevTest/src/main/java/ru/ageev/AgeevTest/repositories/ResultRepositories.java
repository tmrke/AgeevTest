package ru.ageev.AgeevTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ageev.AgeevTest.models.OutputResult;

@Repository
public interface ResultRepositories extends JpaRepository<OutputResult, Integer> {
}
