package ecommerce.project.service;

import ecommerce.project.dto.profile.ProfileRequest;
import ecommerce.project.dto.profile.ProfileResponse;

import java.util.List;

public interface ProfileService{
    ProfileResponse createProfile(ProfileRequest request);
    List<ProfileResponse>getall();
}
