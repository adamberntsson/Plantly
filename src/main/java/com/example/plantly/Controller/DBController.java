package com.example.plantly.Controller;

import com.example.plantly.Domain.Plant;
import com.example.plantly.Domain.User;
import com.example.plantly.Repository.DBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DBController {
    @Autowired
    private DBRepository DBConnection;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/about")
	public String about() {
		return "about";
	}

    @PostMapping("/login")
    public String signup(Model model, @RequestParam String email, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String password) {
        List<User> allUsers = DBConnection.getAllUsers();


        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().equals(email)) {
                model.addAttribute("info", "User with this email already exists");
                return "login";
            }
        }

        DBConnection.addUser(email, firstname, lastname, password);

        return "login";
    }

    @PostMapping("/user")
    public ModelAndView loggedin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {

        boolean userExists = DBConnection.userExists(email, password);
        User user = DBConnection.getCurrentUser(email, password);

        if (userExists) {
            session.setAttribute("user", user);
            return new ModelAndView("userpage").addObject("user", user);
        }
        model.addAttribute("info", "Wrong password or email try again");
        return new ModelAndView("login");

    }

    @GetMapping("/user")
    public ModelAndView userpage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return new ModelAndView("userpage");
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session, HttpServletResponse res) {
        {
            session.invalidate();
            Cookie cookie = new Cookie("jsessionid", "");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/plantinfo")
    public ModelAndView plantinfo() {
        Plant plant = DBConnection.getPlantByPlantSpecies("Monstera Deliciosa");
        return new ModelAndView("plantinfo").addObject("plant", plant);
    }

    @GetMapping("/addplant")
    public String addplant(){
        return "addplant";
    }

    @PostMapping("/addUserPlant")
    public String addUserPlant(@RequestParam String nickName, @RequestParam String plantSpecies, @RequestParam int userId, HttpSession session){
        String poison;
        DBConnection.addPlantToUserPlants(nickName, "needs a image URL", userId, plantSpecies);
        Plant plant = DBConnection.getPlantByPlantSpecies(plantSpecies);
        if(plant.poisonous == 0){
            poison = "no";
        }else{
            poison = "yes";
        }
        session.setAttribute("nickName", nickName);
        session.setAttribute("plantSpecies", plantSpecies);
        session.setAttribute("plantLight", plant.light);
        session.setAttribute("waterDays", plant.daysUntilWatering);
        session.setAttribute("poison", poison);
        return "redirect:/user";
    }

}