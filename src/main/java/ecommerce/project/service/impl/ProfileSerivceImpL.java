package ecommerce.project.service.impl;

import ecommerce.project.dto.profile.ProfileRequest;
import ecommerce.project.dto.profile.ProfileResponse;
import ecommerce.project.entity.Profile;
import ecommerce.project.entity.User;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.respositity.ProfileRepository;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileSerivceImpL implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        User user = checkUser();

        Profile profile = new Profile();

        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setGender(request.gender());
        profile.setTelPhone(request.telPhone());
        profile.setUser(user);

        Profile save = profileRepository.save(profile);

        return new ProfileResponse(
                save.getId(),
                save.getProfileImage(),
                save.getFirstName(),
                save.getLastName(),
                save.getTelPhone(),
                save.getGender()
        );
    }

    @Override
    public List<ProfileResponse> getall() {
        List<Profile> profile=profileRepository.findAll();

        return profile.stream().map(pr0->new ProfileResponse(
                pr0.getId(),
                pr0.getProfileImage(),
                pr0.getFirstName(),
                pr0.getLastName(),
                pr0.getTelPhone(),
                pr0.getGender()
        )).toList();
    }

    private User checkUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
