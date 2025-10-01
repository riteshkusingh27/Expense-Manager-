package com.expensemanager.controller;

import com.expensemanager.dto.Profiledto;
import com.expensemanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<Profiledto> profile(@RequestBody Profiledto profile){
        Profiledto registerdprofile = profileService.registerProfile(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerdprofile);
        // response entity return type is custom http status code with the parameterised object
        // proper way of sending body + status

    }

}
