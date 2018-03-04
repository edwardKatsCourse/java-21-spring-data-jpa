package com.eshop.controller;

import com.eshop.model.User;
import com.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create/{username}")
    public User create(@PathVariable("username") String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

    @GetMapping("/all")
    public List<User> findAll(@RequestParam("page") Integer page,
                              @RequestParam("count") Integer count) {

        Sort.Order userNameSort = Sort.Order
                .by("username")
                .with(Sort.Direction.DESC);


        Sort.Order dateSort = Sort.Order
                .by("createdOn")
                .with(Sort.Direction.ASC);
        Sort sort = Sort.by(userNameSort, dateSort);


        return userRepository
                .findAllByCreatedOnNotNull(
                        PageRequest.of(page, count, sort))
                .getContent();
    }

    @GetMapping("/all-sorted")
    public List<User> getSorted() {
        Sort.Order userNameSort = Sort.Order
                .by("username")
                .with(Sort.Direction.DESC);


        Sort.Order dateSort = Sort.Order
                .by("createdOn")
                .with(Sort.Direction.DESC)
                .nullsNative();




        return userRepository.findAll(Sort.by(userNameSort, dateSort));
    }

    @GetMapping("/max-age")
    public Integer getMaxAge() {
        return userRepository.findMaxAge();
    }
}
