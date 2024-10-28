package br.com.VomHive.VomHive.controller;

import br.com.VomHive.VomHive.model.Company;
import br.com.VomHive.VomHive.model.Product;
import br.com.VomHive.VomHive.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/product")
    public String retornarPagina(Model model) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        return "paginaProduct";
    }

    @GetMapping("/product/nova")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/product/adicionar")
    public String addProduct(@ModelAttribute("product") Product product) {
        productRepo.save(product);
        return "redirect:/product";
    }

    @GetMapping("/product/deletar/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepo.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("/product/editar/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/product/atualizar/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product updatedProduct) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        existingProduct.setTarget(updatedProduct.getTarget());
        existingProduct.setNmProduct(updatedProduct.getNmProduct());
        existingProduct.setDifferential(updatedProduct.getDifferential());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setSalesChannel(updatedProduct.getSalesChannel());
        productRepo.save(existingProduct);
        return "redirect:/product";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campo de Id inválido:" + id));
        model.addAttribute("product", product);
        return "viewProduct";
    }
}
