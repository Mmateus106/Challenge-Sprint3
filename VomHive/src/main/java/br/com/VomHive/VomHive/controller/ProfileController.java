package br.com.VomHive.VomHive.controller;

import br.com.VomHive.VomHive.model.Profile;
import br.com.VomHive.VomHive.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRep;

    @GetMapping("/profiles")
    public String listProfiles(Model model) {
        List<Profile> profiles = profileRep.findAll();
        model.addAttribute("profiles", profiles);
        return "paginaProfile";
    }

    @GetMapping("/profile/nova")
    public String showAddProfileForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "addProfile";
    }

    @PostMapping("/profile/adicionar")
    public String addProfile(@Valid @ModelAttribute("profile") Profile profile, Model model) {
        profileRep.save(profile);
        return "redirect:/profiles";
    }

    @GetMapping("/profile/deletar/{id}")
    public String deleteProfile(@PathVariable("id") Long id) {
        profileRep.deleteById(id);
        return "redirect:/profiles";
    }

    @GetMapping("/profile/editar/{id}")
    public String showEditProfileForm(@PathVariable("id") Long id, Model model) {
        Profile profile = profileRep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("profile", profile);
        return "editProfile";
    }

    @PostMapping("/profile/atualizar/{id}")
    public String updateProfile(@PathVariable("id") Long id, @Valid @ModelAttribute("profile") Profile updatedProfile, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "editProfile";
        }

        Profile existingProfile = profileRep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));


        existingProfile.setNmUser(updatedProfile.getNmUser());
        existingProfile.setPassUser(updatedProfile.getPassUser());
        existingProfile.setPermission(updatedProfile.getPermission());
        existingProfile.setStatus(updatedProfile.getStatus());
        existingProfile.setDtRegister(updatedProfile.getDtRegister());

        profileRep.save(existingProfile);
        return "redirect:/profiles";
    }



    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        Profile profile = profileRep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("profile", profile);
        return "viewProfile";
    }
}
