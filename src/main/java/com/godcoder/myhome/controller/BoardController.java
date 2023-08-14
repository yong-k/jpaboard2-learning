package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired  //--→ BoardValidator 클래스에 @Component 붙여야 사용가능하다. 아니면 에러뜸
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            //-- findById(): Optional 타입 → 여기서는 .orElse(null)을 붙여서 없을 경우 null을 가져오도록 처리
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    //public String boardSubmit(@ModelAttribute Board board) {
    public String boardSubmit(@Valid Board board, BindingResult bindingResult) {

        boardValidator.validate(board, bindingResult);

        // hasErrors(): Board에 우리가 설정한 조건(min=2, max=30)에 부합하지 않으면 false를 return 한다.
        if (bindingResult.hasErrors()) {
            return "board/form";
        }

        // save()
        // → pk가 없을 경우, 새로운 데이터 저장
        // → pk가 있을 경우, 새로운 내용으로 update 해준다.
        boardRepository.save(board);

        return "redirect:/board/list";    // 값을 뿌리지 않고, 바로 이동을 하니까 redirect
    }
}
