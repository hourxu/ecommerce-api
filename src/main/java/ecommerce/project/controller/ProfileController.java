package ecommerce.project.controller;

import ecommerce.project.dto.profile.ProfileRequest;
import ecommerce.project.dto.profile.ProfileResponse;
import ecommerce.project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping
    public ResponseEntity<ProfileResponse> create(@RequestBody ProfileRequest request){
        return ResponseEntity.ok(profileService.createProfile(request));
    }
    @GetMapping
    public ResponseEntity<List<ProfileResponse>>get(){
        return ResponseEntity.ok(profileService.getall());
    }
}
