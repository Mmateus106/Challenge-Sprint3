package br.com.VomHive.VomHive.controller;

import br.com.VomHive.VomHive.model.Profile;
import br.com.VomHive.VomHive.model.Usuario;
import br.com.VomHive.VomHive.repository.ProfileRepository;
import br.com.VomHive.VomHive.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping("/index1")
    public ModelAndView transfIndex() {

        /*
         * Pessoa p1 = new Pessoa(1L, "Fulano", "111.222.333-45", OpcoesPaises.BRASIL,
         * 19); Pessoa p2 = new Pessoa(2L, "Beltrano", "111.222.333-45",
         * OpcoesPaises.BRASIL, 19); Pessoa p3 = new Pessoa(3L, "Joao",
         * "111.222.333-45", OpcoesPaises.BRASIL, 19); Discente d1 = new Discente(1L,
         * "RM1234", p1, OpcoesStatus.ATIVO, OpcoesNivel.GRADUACAO); Discente d2 = new
         * Discente(2L, "RM4567", p2, OpcoesStatus.ATIVO, OpcoesNivel.GRADUACAO);
         * Discente d3 = new Discente(3L, "RM8910", p3, OpcoesStatus.ATIVO,
         * OpcoesNivel.GRADUACAO);
         *
         * List<Discente> lista = new ArrayList<Discente>(); lista.add(d1);
         * lista.add(d2); lista.add(d3);
         */

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        List<Profile> profiles = profileRepo.findAll();

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("var", profiles);

        Optional<Usuario> user = usuarioRepo.findByUsername(username);

        if(user.isPresent()) {
            mv.addObject("nome_usuario",user.get().getNome());
            mv.addObject("img_usuario",user.get().getImgUrl());
        }

        return mv;

    }

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
