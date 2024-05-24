package com.example.boardexam.service;

import com.example.boardexam.domain.Board;
import com.example.boardexam.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoard() {
        return boardRepository.findAll();
    }

    // 페이지네이션
    public Page<Board> findAllBoard(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(sortedByDescId);
    }

    // ID를 통해 게시글 상세 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 삭제
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 등록
    //@Transactional
    public Board writeBoard(Board board) {
        board.setUpdatedAt(LocalDateTime.now());
        board.setCreatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

}
