package ru.ageev.AgeevTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ageev.AgeevTest.models.InputNumber;

@Repository
public interface NumberRepositories extends JpaRepository<InputNumber, Integer> {
}
