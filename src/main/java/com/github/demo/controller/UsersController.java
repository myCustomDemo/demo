package com.github.demo.controller;

import com.github.demo.dao.UserDao;
import com.github.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles the index requests and presents a list of users.
 */
@Controller
public class UsersController {
    //TODO: need to add return response in JSON

    private UserDao userDao;
    private final AtomicLong COUNTER = new AtomicLong();


    @Autowired
    public UsersController(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * GET /  --> Reads all users from database and prints them to index.
     */
    @RequestMapping("/")
    public String index(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    /**
     * GET /create  --> Create a new user and save it in the database.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(int id, String data) {
        try {
            User user = new User(id, data);
            userDao.insert(user);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + COUNTER.incrementAndGet() + " and data: \n" +
                data;
    }

    /**
     * GET /delete  --> Delete the user having the passed id.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(int id) {
        try {
            User user = new User(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }
        return "User " + id + "succesfully deleted!";
    }

    /**
     * GET /update  --> Update the data for the user in the database having the passed id.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(int id, String data) {
        try {
            User user = new User(id);
            userDao.update(user, id, data);
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }
}