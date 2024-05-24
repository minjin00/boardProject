package com.example.boardexam;

import com.example.boardexam.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardExamApplication.class, args);
    }

    // 리스트 나오는지 콘솔에 출력해보기
//    @Bean
//    public CommandLineRunner run(BoardRepository boardRepository) {
//        return args -> {
//            boardRepository.findAll().forEach(System.out::println);
//        };
//    }

}
