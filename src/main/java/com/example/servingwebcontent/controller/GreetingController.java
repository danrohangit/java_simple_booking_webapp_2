package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.DateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.time.LocalTime;

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
	
	// map http post request for url path /booking
    @PostMapping("/book")
    public String book(@RequestParam("selectedDate") String selectedDate,Model model) {
        model.addAttribute("date", selectedDate);
		
        // Define the list of available times
        List<String> timeList = Arrays.asList("11:00", "12:00", "13:00","14:00","15:00","16:00","17:00",
		"18:00","19:00");		
		
        // Define a new list to hold the available times that are later than the current time
        List<String> newTimeList = new ArrayList<>();	
		
		try{
			String theTime = dateModel.getCurrentDateTime();
			
			String dateOnly = theTime.substring(0, 10);
			
			String timeOnly = theTime.substring(11);
			
			if(selectedDate.equals(dateOnly)){
				
				LocalTime timeOnlyParsed = LocalTime.parse(timeOnly);
				
				timeOnlyParsed = timeOnlyParsed.plusHours(1);
				
				// Loop through the available times
				for (String time : timeList) {
					// Parse the time string into a LocalTime object
					LocalTime availableTime = LocalTime.parse(time);

					// Compare the current time to the available time
					if (timeOnlyParsed.compareTo(availableTime) < 0) {
						// The available time is later than or equal to the current time,
						// so add it to the new list
						
						String theResult = dateModel.checkMaxBookings(selectedDate,time);
						
						if (theResult == "possible"){
							newTimeList.add(time);
						}
						
						//newTimeList.add(time);
					}
				}
				model.addAttribute("timeList", newTimeList);
			}
			
			else{
				
				// Loop through the available times
				for (String time : timeList) {

					String theResult = dateModel.checkMaxBookings(selectedDate,time);
					
					if (theResult == "possible"){
						newTimeList.add(time);
					}
				}				
				
				model.addAttribute("timeList", newTimeList);
				
			}
			
		}	catch (Exception e) {
			//add the error message to model as attribute
			model.addAttribute("date", "Error: " + e.getMessage());
		}
		
        return "book";
    }
	
	// map http post request for url path /booking
    @PostMapping("/postBook")
    public String postBook(@RequestParam("selectedDate") String selectedDate,
	@RequestParam("selectedTime") String selectedTime,
	@RequestParam("selectedName") String selectedName,
	@RequestParam("selectedEmail") String selectedEmail,
	@RequestParam("selectedPhone") String selectedPhone,
	@RequestParam("selectedLocation") String selectedLocation,Model model) {
		
		try{
			String theResult = dateModel.startBook(selectedDate,selectedTime,selectedName,selectedEmail,selectedPhone,selectedLocation);	
			
			String bookedString = "Booked!";

			model.addAttribute("bookedString",bookedString);	
			
		}	catch (Exception e) {
			e.printStackTrace();
			String catchedString = selectedDate + " " + selectedTime + " " + selectedName + " " + selectedEmail + " " + selectedPhone + " " + selectedLocation;
			//add the error message to model as attribute
			model.addAttribute("bookedString", catchedString);
		}	

        return "index";
    }	

}