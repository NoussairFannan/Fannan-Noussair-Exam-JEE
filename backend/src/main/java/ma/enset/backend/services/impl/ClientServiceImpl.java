package ma.enset.backend.services.impl;

import ma.enset.backend.dtos.ClientDTO;
import ma.enset.backend.entities.Client;
import ma.enset.backend.mappers.ClientMapper;
import ma.enset.backend.repositories.ClientRepository;
import ma.enset.backend.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return clientMapper.fromClient(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = clientMapper.toClient(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        // Check if client exists
        clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientDTO.getId()));
        
        Client client = clientMapper.toClient(clientDTO);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.fromClient(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        // Check if client exists
        clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        clientRepository.deleteById(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<ClientDTO> searchClientsByName(String name) {
        // This is a simple implementation. In a real application, you might want to use
        // a more sophisticated search mechanism like Spring Data JPA's Specification or QueryDSL.
        List<Client> clients = clientRepository.findAll().stream()
                .filter(client -> client.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> getClientsWithCredits() {
        List<Client> clients = clientRepository.findAll().stream()
                .filter(client -> client.getCredits() != null && !client.getCredits().isEmpty())
                .collect(Collectors.toList());
        
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }
}