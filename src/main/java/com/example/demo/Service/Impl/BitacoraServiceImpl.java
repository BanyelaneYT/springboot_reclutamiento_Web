package com.example.demo.Service.Impl;

import com.example.demo.Repository.BitacoraRepository;
import com.example.demo.Service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Override
    public List<Map<String, Object>> listarBitacora() {
        return bitacoraRepository.listarBitacora();
    }
}
