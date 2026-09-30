package com.models.practiceproject.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.models.practiceproject.Entity.JournelEntry;


@RestController
@RequestMapping("journel")
public class ApiEndPoints {
    Map<String,JournelEntry> map = new HashMap<>();

    @GetMapping
    public List<JournelEntry> getAll(){
        return new ArrayList<>(map.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournelEntry entry){
        map.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("/{id}")
    public JournelEntry getById(@PathVariable Long id){
        return map.get(id);
    }
}
