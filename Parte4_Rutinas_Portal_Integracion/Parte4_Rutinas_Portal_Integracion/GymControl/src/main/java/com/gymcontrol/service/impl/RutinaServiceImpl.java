package com.gymcontrol.service.impl;

import com.gymcontrol.domain.Rutina;
import com.gymcontrol.repository.RutinaRepository;
import com.gymcontrol.service.RutinaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaServiceImpl implements RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;

    @Override
    public List<Rutina> listarTodos() {
        return rutinaRepository.findAll();
    }

    @Override
    public List<Rutina> listarPorCliente(Long clienteId) {
        return rutinaRepository.findByClienteIdOrderByIdDesc(clienteId);
    }

    @Override
    public Rutina guardar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    @Override
    public Rutina buscarPorId(Long id) {
        return rutinaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        rutinaRepository.deleteById(id);
    }
}
