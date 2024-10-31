package br.com.VomHive.VomHive.controller;

import br.com.VomHive.VomHive.model.Company;
import br.com.VomHive.VomHive.model.Product;
import br.com.VomHive.VomHive.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepo;

    @GetMapping("/company")
    public String retornarPagina(Model model) {
        List<Company> companies = companyRepo.findAll();
        model.addAttribute("companies", companies);
        return "paginaCompany";
    }

    @GetMapping("/company/nova")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "addCompany";
    }

    @PostMapping("/company/adicionar")
    public String addCompany(@ModelAttribute("company") Company company) {
        companyRepo.save(company);
        return "redirect:/company";
    }

    @GetMapping("/company/deletar/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyRepo.deleteById(id);
        return "redirect:/company";
    }

    @GetMapping("/company/editar/{id}")
    public String showEditCompanyForm(@PathVariable("id") Long id, Model model) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("company", company);
        return "editCompany";
    }

    @PostMapping("/company/atualizar/{id}")
    public String updateCompany(@PathVariable("id") Long id, @ModelAttribute("company") Company updatedCompany) {
        Company existingCompany = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        existingCompany.setNmCompany(updatedCompany.getNmCompany());
        existingCompany.setCnpj(updatedCompany.getCnpj());
        existingCompany.setEmail(updatedCompany.getEmail());
        existingCompany.setDtRegister(updatedCompany.getDtRegister());
        companyRepo.save(existingCompany);
        return "redirect:/company";
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public String viewCompany(@PathVariable("id") Long id, Model model) {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("company", company);
        return "viewCompany";
    }

}

