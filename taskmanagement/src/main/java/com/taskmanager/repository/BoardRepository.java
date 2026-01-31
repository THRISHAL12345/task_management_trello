package com.taskmanager.repository;

import com.taskmanager.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {

    Page<Board> findByOwnerId(String ownerId, Pageable pageable);

    Optional<Board> findByIdAndOwnerId(String id, String ownerId);
}
