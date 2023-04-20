package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.DateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;

@Controller
public class GreetingController {
	
	// auto inject and instantiate dateModel field of GreetingController class
    @Autowired
    private DateModel dateModel;
	
	// map http get request for url path /booking
    @GetMapping("/booking")
	
	//retrieve the date list
	public String getDates(Model model) {
		try{
			//add the list of dates to the theDates list
			List<String> theDates = dateModel.getCurrentAndFutureDates();
			
			try{
				String currentDateTime = dateModel.getCurrentDateTime();
			} catch (Exception e) {
				model.addAttribute("currentDateTime", "Error: " + e.getMessage());
			}
			
			try{
				Dictionary<String, String> dict = new Hashtable<String, String>() {{
					put("01", "Jan");
					put("02", "Feb");
					put("03", "Mar");
					put("04", "Apr");
					put("05", "May");
					put("06", "Jun");
					put("07", "Jul");
					put("08", "Aug");
					put("09", "Sep");
					put("10", "Oct");
					put("11", "Nov");
					put("12", "Dec");
				}};
				
				//create a list of string datesOfFirstMonth
				List<String> datesOfFirstMonth = new ArrayList<>();

				//create a list of string datesOfSecondMonth
				List<String> datesOfSecondMonth = new ArrayList<>();					
				
				// Create a set to store the month abbreviations
				Set<String> months = new HashSet<>();

				// Loop over the list of dates and add the month abbreviation for each date to the set
				for (String date : theDates) {
					// Extract the month number from the date string
					String monthNumber = date.substring(5, 7);
					
					// Get the month abbreviation from the dictionary and add it to the set
					String monthAbbr = dict.get(monthNumber);
					if (monthAbbr != null) {
						months.add(monthAbbr);
					}
					
					int count1 = months.size();
					
					if (count1<2) {
						datesOfFirstMonth.add(date);
					}
					else{
						datesOfSecondMonth.add(date);
					}
					
				}
				
				model.addAttribute("months", months);
				model.addAttribute("datesOfFirstMonth", datesOfFirstMonth);
				model.addAttribute("datesOfSecondMonth", datesOfSecondMonth);
				
				
			}	catch (Exception e) {
				//add the error message to model as attribute
				model.addAttribute("theDates", "Error: " + e.getMessage());
			}			
			
		}	catch (Exception e) {
			//add the error message to model as attribute
			model.addAttribute("theDates", "Error: " + e.getMessage());
		}
		
		//return url path
		return "booking";		
	}

	/*	
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
		
        try {
            String currentDateTime = dateModel.getCurrentDateTime();
			
			//add currentDateTime attribute to model
            model.addAttribute("currentDateTime", currentDateTime);
        } catch (Exception e) {
            model.addAttribute("currentDateTime", "Error: " + e.getMessage());
        }
		
		//return url path
		return "booking";
	}
	*/		
	
	/*
	// retrieve date with sql
    public String getCurrentDateTime(Model model) {
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