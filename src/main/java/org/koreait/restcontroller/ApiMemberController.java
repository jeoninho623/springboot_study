package org.koreait.restcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.commons.JSONData;
import org.koreait.entities.Member;
import org.koreait.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberRepository repository;

    @GetMapping("/{userId}")
    public ResponseEntity<JSONData> info(@PathVariable String userId) {
        Member member = repository.findByUserId(userId);

        JSONData<Member> data = new JSONData<>(member);

        boolean isError = true;
        if(isError) {
            throw new RuntimeException("에러 발생!!!");
        }

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/list")
    public List<Member> list() {
        List<Member> members = (List<Member>) repository.findAll();

        return members;
    }

    @GetMapping("/hello")
    public String hello() {
        return "계절은 돌고 돌아 도라에몽!";
    }

    @GetMapping("/test")
    public void test() {
        log.info("테숫후!");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid RequestLogin form, Errors errors) {

        if(errors.hasErrors()) {
            String message = errors.getAllErrors().stream()
                    .map(o -> o.getDefaultMessage())
                    .collect(Collectors.joining(","));

            throw new RuntimeException(message);
        }

        log.info(form.toString());

        return ResponseEntity.ok().build();

        /*return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("TestHeader","Test")
                .build();*/
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {


        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(HttpStatus.BAD_REQUEST);
        data.setMessage(e.getMessage());

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
