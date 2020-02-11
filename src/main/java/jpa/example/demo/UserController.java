package jpa.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
     * Customized by "spring.data.web.pageable."
     * 但是此处 page size 的默认值由 `@PageableDefault` 注解决定, 不指定的话还是 page: 0, size: 10.
     * `one-indexed-parameters` 让页数从1开始计算 (0和1都认为是开始的第一页).
     *
     * @return
     */
    @GetMapping
    public Page<User> index(@SortDefault.SortDefaults({
            @SortDefault(sort = "name"),
            @SortDefault(sort = "email", direction = Sort.Direction.DESC),})
                            @PageableDefault(size = 2)
                                    Pageable pageable) {
        return userRepository.findAll(pageable);
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
