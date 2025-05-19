package ma.enset.backend.services;

import ma.enset.backend.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    // CRUD operations
    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO updateClient(ClientDTO clientDTO);
    void deleteClient(Long id);
    
    // Additional business operations
    List<ClientDTO> searchClientsByName(String name);
    List<ClientDTO> getClientsWithCredits();
}