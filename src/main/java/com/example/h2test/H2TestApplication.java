package com.example.h2test;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wangbin
 */
@SpringBootApplication
@RestController
public class H2TestApplication {

  @Resource
  private UserRepository userRepository;

  @GetMapping("/")
  public ResponseEntity list() {
    return ResponseEntity.ok(userRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity findByName(@PathVariable("id") String id) {
    return ResponseEntity.ok(userRepository.findById(id));
  }

  public static void main(String[] args) {
    SpringApplication.run(H2TestApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository repository) {
    return args -> {
      repository.save(new User("张三", "pwd1"));
      repository.save(new User("李四", "pwd2"));
      repository.save(new User("王五", "pwd3"));
      repository.save(new User("赵六", "pwd4"));
    };
  }


}

@Repository
interface UserRepository extends JpaRepository<User, String> {
}

@Data
@Entity
@Table(name = "t_user")
@NoArgsConstructor
class User {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  private String id;
  private String username;
  private String password;

  User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}