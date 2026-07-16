package com.gymcontrol.controller;

import com.gymcontrol.domain.Usuario;
import com.gymcontrol.service.PagoService;
import com.gymcontrol.service.RutinaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientePortalController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/mi-rutina")
    public String miRutina(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioCliente(session, redirectAttributes);
        if (usuario == null) return "redirect:/login";
        if (usuario.getCliente() == null) return "redirect:/home";
        model.addAttribute("usuario", usuario);
        model.addAttribute("rutinas", rutinaService.listarPorCliente(usuario.getCliente().getId()));
        return "cliente/mi-rutina";
    }

    @GetMapping("/mis-pagos")
    public String misPagos(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioCliente(session, redirectAttributes);
        if (usuario == null) return "redirect:/login";
        if (usuario.getCliente() == null) return "redirect:/home";
        model.addAttribute("usuario", usuario);
        model.addAttribute("pagos", pagoService.listarPorCliente(usuario.getCliente().getId()));
        return "cliente/mis-pagos";
    }

    private Usuario usuarioCliente(HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioEnSesion");
        if (usuario == null) return null;
        if (!"CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            redirectAttributes.addFlashAttribute("mensaje", "Esta opción es exclusiva para clientes.");
        }
        return usuario;
    }
}
