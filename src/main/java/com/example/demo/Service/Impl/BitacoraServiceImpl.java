package com.example.demo.Service.Impl;

import com.example.demo.Repository.BitacoraRepository;
import com.example.demo.Service.BitacoraService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    private final BitacoraRepository repo;

    public BitacoraServiceImpl(BitacoraRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Map<String, Object>> listarBitacora() {
        return repo.listarBitacora();
    }

    @Override
    public void registrarBitacora(int idUsuario, int idRecluta, String accion) {
        repo.registrarBitacora(idUsuario, idRecluta, accion);
    }
}
