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
    public Profiledto registerProfile(Profiledto profiledto){
        ProfileEntity newprofile = toEntity(profiledto);
        newprofile.setActivationToken(UUID.randomUUID().toString());
        newprofile = profileRepo.save(newprofile);
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
