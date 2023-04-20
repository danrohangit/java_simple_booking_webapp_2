package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.DateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {
	
	// auto inject and instantiate dateModel field of GreetingController class
    @Autowired
    private DateModel dateModel;
	
	// map http get request for url path /booking
    @GetMapping("/booking")
	
	// retrieve the date list
	public String getDates(Model model) {
		try{
			//add the list of dates to the theDates list
			List<String> theDates = dateModel.getCurrentAndFutureDates();
			
			//add theDates list to model as attribute
			model.addAttribute("theDates", theDates);
			
		}	catch (Exception e) {
			//add the error message to model as attribute
			model.addAttribute("theDates", "Error: " + e.getMessage());
		}
		
		//return url path
		return "booking";
	}
		
	
	/*
	// retrieve date with sql
    public String getDate(Model model) {
        try {
            String currentDate = dateModel.getCurrentDate();
			
			//add currentDate attribute to model
            model.addAttribute("currentDate", currentDate);
        } catch (Exception e) {
            model.addAttribute("currentDate", "Error: " + e.getMessage());
        }
		
		//return url path
        return "booking";
    }	
	*/
	
	/*
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        try {
            String currentDate = dateModel.getCurrentDate();
            model.addAttribute("currentDate", currentDate);
        } catch (Exception e) {
            model.addAttribute("currentDate", "Error: " + e.getMessage());
        }
        return "greeting";
    }
	*/

}