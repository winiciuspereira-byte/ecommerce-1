package br.com.ecommerce.controllers;

import br.com.ecommerce.models.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();
    private Long contador = 1L;

    public ProdutoController() {
        produtos.add(new Produto(contador++, "Headphone Sony", "Usado poucas vezes", 200, "/img/Headphone Sony.jpg"));
        produtos.add(new Produto(contador++, "Controle PS5", "Semi novo", 200, "/img/controle miranha.jpg"));
        produtos.add(new Produto(contador++, "Macbook Air M1", "256GB | 8GB RAM", 3200, "/img/Macbook Air M1.jpg"));
        produtos.add(new Produto(contador++, "Apple HomePod", "Som top", 479, "/img/Apple HomePod.jpg"));
        produtos.add(new Produto(contador++, "Canon DSLR", "Profissional", 10200, "/img/Canon DSLR.jpg"));
        produtos.add(new Produto(contador++, "iPad 10th", "Novo", 2000, "/img/Ipad 10th.jpg"));
    }

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {

        // 🔥 GARANTE QUE SEMPRE SALVA CERTO
        if (!produto.getImagem().startsWith("/img/")) {
            produto.setImagem("/img/" + produto.getImagem());
        }

        produto.setId(contador++);
        produtos.add(produto);

        return "redirect:/";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        produtos.removeIf(p -> p.getId().equals(id));
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("produto", produto);
        return "form";
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute Produto produto) {

        if (!produto.getImagem().startsWith("/img/")) {
            produto.setImagem("/img/" + produto.getImagem());
        }

        produtos.removeIf(p -> p.getId().equals(produto.getId()));
        produtos.add(produto);

        return "redirect:/";
    }
}