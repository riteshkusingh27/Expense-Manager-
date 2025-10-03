package com.expensemanager.controller;

import com.expensemanager.dto.AuthDto;
import com.expensemanager.dto.Profiledto;
import com.expensemanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
      @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam("token") String token){
        boolean isValid = profileService.validateToken(token);
        if(isValid){
            return ResponseEntity.ok("Profile activated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation token");
        }
    }
   @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AuthDto authdto){
          try {
      if(!profileService.isAccountActive(authdto.getEmail())){
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error","Account not activated. Please check your email for activation link."));
      }
      Map<String,Object> respones = profileService.authenticateAndgenerateToken(authdto);
      return ResponseEntity.ok(respones);
          } catch(Exception e){
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid email or password"));
          }
    }

}
