package cn.maxiangshun.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.maxiangshun.file.**"})
@MapperScan(basePackages = {"cn.maxiangshun.file.mapper.**"})
public class FileServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(FileServiceStarter.class, args);
    }
}
