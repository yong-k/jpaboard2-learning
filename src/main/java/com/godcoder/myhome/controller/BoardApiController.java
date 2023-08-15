package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardApiController {

    // https://spring.io/guides/tutorials/rest/

    @Autowired
    private BoardRepository boardRepository;

    // 제목&내용 으로 검색할 수 있도록 추가해보자
    /*
    @GetMapping("/boards")
    List<Board> all() {
        return boardRepository.findAll();
    }
    */
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return boardRepository.findAll();
        } else {
            //return boardRepository.findByTitle(title);
            return boardRepository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return boardRepository.findById(id).orElse(null);   // 없을경우 null return
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}
