package com.gymcontrol.service;

import com.gymcontrol.domain.Rutina;
import java.util.List;

public interface RutinaService {
    List<Rutina> listarTodos();
    List<Rutina> listarPorCliente(Long clienteId);
    Rutina guardar(Rutina rutina);
    Rutina buscarPorId(Long id);
    void eliminar(Long id);
}
