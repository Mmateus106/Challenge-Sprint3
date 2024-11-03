package br.com.VomHive.VomHive.controller;

import br.com.VomHive.VomHive.model.Profile;
import br.com.VomHive.VomHive.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    @GetMapping("/profile")
    public String retornarPagina(Model model) {
        List<Profile> profiles = profileRepo.findAll();
        model.addAttribute("profiles", profiles);
        return "paginaProfile";
    }

    @GetMapping("/profile/nova")
    public String showAddProfileForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "addProfile";
    }

    @PostMapping("/profile/adicionar")
    public String addProfile(@ModelAttribute("profile") Profile profile) {
        profileRepo.save(profile);
        return "redirect:/profile";
    }

    @GetMapping("/profile/deletar/{id}")
    public String deleteProfile(@PathVariable("id") Long id) {
        profileRepo.deleteById(id);
        return "redirect:/profile";
    }

    @GetMapping("/profile/editar/{id}")
    public String showEditProfileForm(@PathVariable("id") Long id, Model model) {
        Profile profile = profileRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("profile", profile);
        return "editProfile";
    }

    @PostMapping("/profile/atualizar/{id}")
    public String updateProfile(@PathVariable("id") Long id, @ModelAttribute("profile") Profile updatedProfile) {
        Profile existingProfile = profileRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        existingProfile.setNmUser(updatedProfile.getNmUser());
        existingProfile.setPassUser(updatedProfile.getPassUser());
        existingProfile.setPermission_status(updatedProfile.getPermission_status());
        existingProfile.setStatus(updatedProfile.getStatus());
        existingProfile.setDtRegister(updatedProfile.getDtRegister());
        profileRepo.save(existingProfile);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        Profile profile = profileRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("profile", profile);
        return "viewProfile";
    }
}
