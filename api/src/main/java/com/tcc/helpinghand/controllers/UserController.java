package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.controllers.response.UserDataResponse;
import com.tcc.helpinghand.controllers.response.UserResponse;
import com.tcc.helpinghand.models.Achievement;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.AchievementService;
import com.tcc.helpinghand.services.LogService;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private AchievementService achievementService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<UserResponse> getAllWithoutId() {
        return userService.getAllWithoutId();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<User> searchByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/limitedSearch")
    public List<UserDataResponse> limitedSearchByName(@RequestParam("name") String name) {
        return userService.findLimitedDataByName(name);
    }

    @GetMapping("/searchByDate")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByDates(
            @RequestParam("initial") @DateTimeFormat(pattern = "dd-MM-yyyy") Date initial,
            @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end
    ) {
        return userService.findByRegistrationDate(initial, end);
    }

    @GetMapping("/paginated")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsersPagination(
            @RequestParam("pages") int pages,
            @RequestParam("elements") int elements
    ) {
        Pageable pageable = PageRequest.of(pages, elements);
        return userService.getAllPaginated(pageable);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User edit(@PathVariable long id, @RequestBody User user) {
        return userService.edit(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        logService.removeLogByUserId(id);
        userService.delete(id);
    }

    //
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser.getUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}/achievements")
    public List<Achievement> getUserAchievements(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();

        return achievementService.getUserAchievements(user);
    }

}
