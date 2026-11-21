package com.pruebatec.msclientes.infrastructure.adapter.out.persistence;

import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.mapper.ClientePersistenceMapper;
import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.repository.JpaClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final JpaClienteRepository jpaRepository;
    private final ClientePersistenceMapper mapper;

    public ClienteRepositoryAdapter(JpaClienteRepository jpaRepository,
                                    ClientePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        entity.getDirecciones().forEach(d -> d.setCliente(entity));
        ClienteEntity guardado = jpaRepository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {

        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
