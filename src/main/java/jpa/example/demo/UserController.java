package jpa.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Hibernate: select user0_.id as id1_0_, user0_.email as email2_0_, user0_.name as name3_0_ from user user0_ order by user0_.name asc, user0_.email desc limit ?, ?
     * Hibernate: select count(user0_.id) as col_0_0_ from user user0_
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public Page<User> index(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (size == null) {
            size = 20;
        }
        if (page == null) {
            page = 1;
        }
        page = (page >= 1) ? page : 1;

        Sort sort = Sort.by(Sort.Order.asc("name"), Sort.Order.desc("email"));
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        return userRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    public Optional<User> show(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public void create(@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
