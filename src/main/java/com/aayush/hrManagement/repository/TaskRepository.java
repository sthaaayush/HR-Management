package com.aayush.hrManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aayush.hrManagement.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
