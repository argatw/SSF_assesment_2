package vttp2022.ssf.assessment.videosearch.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.ssf.assessment.videosearch.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService seaServ;

    @GetMapping(path = "search")
    public String search(@RequestParam (name = "searchString", required = true) String searchString, 
        @RequestParam(name = "number", required = false) Integer count, Model model) {
            if (count == null) {
                count = 10;
            }
            
            System.out.printf(">>> %s\n", searchString);

            model.addAttribute("games", seaServ.search(searchString, count));
            // model.addAttribute("search", search);
            // model.addAttribute("number", number);
            
            return "result";
        
      
    }
}
