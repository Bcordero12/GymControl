package com.gymcontrol.config;

import com.gymcontrol.domain.Cliente;
import com.gymcontrol.domain.Pago;
import com.gymcontrol.domain.Rutina;
import com.gymcontrol.domain.Usuario;
import com.gymcontrol.repository.ClienteRepository;
import com.gymcontrol.repository.PagoRepository;
import com.gymcontrol.repository.RutinaRepository;
import com.gymcontrol.repository.UsuarioRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private RutinaRepository rutinaRepository;
    @Autowired private PagoRepository pagoRepository;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByUsuario("admin") == null) {
            Usuario admin = new Usuario();
            admin.setUsuario("admin");
            admin.setContraseña(sha256("admin123"));
            admin.setNombre("Administrador");
            admin.setEmail("admin@gymcontrol.com");
            admin.setRol("ADMIN");
            admin.setActivo(true);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByUsuario("cliente1") == null) {
            Cliente cliente = clienteRepository.findAll().stream().findFirst().orElseGet(() -> {
                Cliente nuevo = new Cliente();
                nuevo.setNombre("Cliente de prueba");
                nuevo.setCedula("111111111");
                nuevo.setTelefono("8888-8888");
                nuevo.setCorreo("cliente@gymcontrol.com");
                nuevo.setFechaRegistro(LocalDate.now());
                return clienteRepository.save(nuevo);
            });

            Usuario usuarioCliente = new Usuario();
            usuarioCliente.setUsuario("cliente1");
            usuarioCliente.setContraseña(sha256("cliente123"));
            usuarioCliente.setNombre(cliente.getNombre());
            usuarioCliente.setEmail(cliente.getCorreo());
            usuarioCliente.setRol("CLIENTE");
            usuarioCliente.setActivo(true);
            usuarioCliente.setCliente(cliente);
            usuarioRepository.save(usuarioCliente);

            if (rutinaRepository.findByClienteIdOrderByIdDesc(cliente.getId()).isEmpty()) {
                Rutina rutina = new Rutina();
                rutina.setNombre("Rutina personal inicial");
                rutina.setDescripcion("Calentamiento 10 minutos, sentadillas 3x12, press de pecho 3x10 y estiramiento final.");
                rutina.setCliente(cliente);
                rutinaRepository.save(rutina);
            }
            if (pagoRepository.findByClienteIdOrderByFechaPagoDesc(cliente.getId()).isEmpty()) {
                Pago pago = new Pago();
                pago.setMonto(25000.0);
                pago.setFechaPago(LocalDate.now());
                pago.setCliente(cliente);
                pagoRepository.save(pago);
            }
        }
    }

    private String sha256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte b : hash) resultado.append(String.format("%02x", b));
            return resultado.toString();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo cifrar la contraseña", e);
        }
    }
}
