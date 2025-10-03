package com.expensemanager.service;

import com.expensemanager.dto.AuthDto;
import com.expensemanager.dto.Profiledto;
import com.expensemanager.entity.ProfileEntity;
import com.expensemanager.repository.ProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor   // internally uses component annotation
public class ProfileService {
    private final ProfileRepo  profileRepo;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final PasswordEncoder pas;
    public Profiledto registerProfile(Profiledto profiledto){
        ProfileEntity newprofile = toEntity(profiledto);
        newprofile.setActivationToken(UUID.randomUUID().toString());
//        newprofile.setPassword(pas.encode(newprofile.getPassword()));
        newprofile = profileRepo.save(newprofile);
        //send activation email
        String activationlink = "http://localhost:8080/api/v1.0/activate?token="+newprofile.getActivationToken();
        String subject = "Activate your Expense tracker account";
        String body = "Click on the following to activate your Expense tracker account: " + activationlink;
        emailService.sendEmail(newprofile.getEmail(),subject,body);
        // again convert the saved entity to dto  (response)
       return  toDto(newprofile);

    }

    // helper to convert dto to entity object
    public ProfileEntity toEntity(Profiledto profiledto){
        return ProfileEntity.builder()
                .id(profiledto.getId())
                .fullName(profiledto.getFullName())
                .email(profiledto.getEmail())
                .password(pas.encode(profiledto.getPassword()))
                .createdAt(profiledto.getCreatedAt())
                .updatedAt(profiledto.getUpdatedAt())
                .build();
    }
    // helper to convert entity to dto
    public Profiledto toDto(ProfileEntity profileEntity){
        return Profiledto.builder().
          id(profileEntity.getId()).
                fullName(profileEntity.getFullName()).
                email(profileEntity.getEmail()).
                profileImageUrl(profileEntity.getProfileImageUrl()).
                createdAt(profileEntity.getCreatedAt()).
                updatedAt(profileEntity.getUpdatedAt()).


                build();
    }

    // validate the token

    public boolean validateToken(String token){
         return profileRepo.findByActivationToken(token).map(profile->{
             profile.setIsActive(true);
             profileRepo.save(profile);
             return true ;
         })
                 .orElse(false);
    }

    // isaccount active
    public boolean isAccountActive(String email){
        return profileRepo.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }
    // current profile
    public ProfileEntity getcurrentProfile(){
      Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        return profileRepo.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("Profile not found"));
    }

    public Profiledto getpublicProfile (String email){
        ProfileEntity currentuser = null ;
        if(email==null){
           currentuser= getcurrentProfile();
        }
        else{
           currentuser =   profileRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("profile is not found with email "+email));
        }
        return Profiledto.builder()
                .id(currentuser.getId())
                .fullName(currentuser.getFullName())
                .email(currentuser.getEmail())
                .profileImageUrl(currentuser.getProfileImageUrl())
                .createdAt(currentuser.getCreatedAt())
                .updatedAt(currentuser.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticateAndgenerateToken(AuthDto authdto) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authdto.getEmail(), authdto.getPassword()));
            // generate jwt token
            return Map.of(
                    "token" , "jwttoken",
                    "user" , getpublicProfile(authdto.getEmail())
            );
        } catch (Exception e){
            throw new RuntimeException("Invalid email or password");
        }
    }
}
