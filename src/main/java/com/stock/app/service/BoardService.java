package com.stock.app.service;

import com.stock.app.dto.BoardDto;
import com.stock.app.dto.NewsDto;
import com.stock.app.entity.Board;
import com.stock.app.entity.News;
import com.stock.app.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .country(board.getCountry())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedTime())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .country(board.getCountry())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedTime())
                .build();
        return boardDto;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> searchBoard(String query) {
        List<Board> boardList = boardRepository.findByTitleContainingOrContentContaining(query, query);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .country(board.getCountry())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedTime())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }
}