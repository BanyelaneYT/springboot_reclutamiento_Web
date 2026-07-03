package com.example.demo.Service.Impl;

import com.example.demo.Repository.BitacoraRepository;
import com.example.demo.Service.BitacoraService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraServiceImpl(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

        @Override
    public List<Map<String, Object>> listarBitacora() {
        return bitacoraRepository.listarBitacora();
    }

    @Override
    public void registrarBitacora(int idUsuario, int idRecluta, String accion) {
        bitacoraRepository.registrarBitacora(idUsuario, idRecluta, accion);
    }
}
