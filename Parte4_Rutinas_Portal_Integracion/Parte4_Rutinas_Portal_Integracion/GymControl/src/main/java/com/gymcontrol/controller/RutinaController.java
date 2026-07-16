package com.gymcontrol.controller;

import com.gymcontrol.domain.Rutina;
import com.gymcontrol.service.ClienteService;
import com.gymcontrol.service.RutinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    public String listar(Model model) {
        model.addAttribute("rutinas", rutinaService.listarTodos());
        return "rutinas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("rutina", new Rutina());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "rutinas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Rutina rutina, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            return "rutinas/form";
        }
        rutinaService.guardar(rutina);
        return "redirect:/rutinas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("rutina", rutinaService.buscarPorId(id));
        model.addAttribute("clientes", clienteService.listarTodos());
        return "rutinas/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        rutinaService.eliminar(id);
        return "redirect:/rutinas";
    }
}
