package com.expensemanager.service;

import com.expensemanager.dto.Profiledto;
import com.expensemanager.entity.ProfileEntity;
import com.expensemanager.repository.ProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor   // internally uses component annotation
public class ProfileService {
    private final ProfileRepo  profileRepo;
    private final EmailService emailService;
    public Profiledto registerProfile(Profiledto profiledto){
        ProfileEntity newprofile = toEntity(profiledto);
        newprofile.setActivationToken(UUID.randomUUID().toString());
        newprofile = profileRepo.save(newprofile);
        //send activation email
        String activationlink = "http:localhost:8080/api/v1.0/activate?token="+newprofile.getActivationToken();
        String subject = "Activate your Expense tracker account";
        String body = "Click on the following to activate your Expense tracker account:" + activationlink;
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
                .password(profiledto.getPassword())
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

}
