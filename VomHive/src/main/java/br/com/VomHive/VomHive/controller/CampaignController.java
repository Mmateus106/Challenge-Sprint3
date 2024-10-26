package br.com.VomHive.VomHive.controller;
import br.com.VomHive.VomHive.model.Campaign;
import br.com.VomHive.VomHive.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CampaignController {

    @Autowired
    private CampaignRepository rep;

    // Controllers Campaign

    @GetMapping("/teste")
    public String retornarPagina(Model model) {
        List<Campaign> campaigns = rep.findAll();
        model.addAttribute("campaigns", campaigns);
        return "pagina";
    }

    @GetMapping("/campaign/nova")
    public String showAddCampaignForm(Model model) {
        model.addAttribute("campaign", new Campaign());
        return "addCampaign";
    }

    @PostMapping("/campaign/adicionar")
    public String addCampaign(@ModelAttribute("campaign") Campaign campaign) {
        rep.save(campaign);
        return "redirect:/teste";
    }

    @GetMapping("/campaign/deletar/{id}")
    public String deleteCampaign(@PathVariable("id") Long id) {
        rep.deleteById(id);
        return "redirect:/teste";
    }

    @GetMapping("/campaign/editar/{id}")
    public String showEditCampaignForm(@PathVariable("id") Long id, Model model) {
        Campaign campaign = rep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("campaign", campaign);
        return "editCampaign";
    }

    @PostMapping("/campaign/atualizar/{id}")
    public String updateCampaign(@PathVariable("id") Long id, @ModelAttribute("campaign") Campaign updatedCampaign) {
        Campaign existingCampaign = rep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        existingCampaign.setNmCampaign(updatedCampaign.getNmCampaign());
        existingCampaign.setDetails(updatedCampaign.getDetails());
        existingCampaign.setStatus(updatedCampaign.getStatus());
        existingCampaign.setObjective(updatedCampaign.getObjective());
        existingCampaign.setBudget(updatedCampaign.getBudget());
        existingCampaign.setDtRegister(updatedCampaign.getDtRegister());
        rep.save(existingCampaign);
        return "redirect:/teste";
    }

    @RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET)
    public String viewCampaign(@PathVariable("id") Long id, Model model) {
        Campaign campaign = rep.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("campaign", campaign);
        return "viewCampaign";
    }


}
