package com.company.controllers;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.model.Profession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/api")
public class CommonController {
    @RequestMapping(value = "addDep", method = RequestMethod.GET)
    public String addDepartment(Model model){

        model.addAttribute("department", new Department());
        return "department";

    }


    @GetMapping("/addProf")
    public String addProf(Model model){

        model.addAttribute("profession", new Profession());
        return "profession";

    }
}
