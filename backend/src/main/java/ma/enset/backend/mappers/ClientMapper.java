package ma.enset.backend.mappers;

import ma.enset.backend.dtos.ClientDTO;
import ma.enset.backend.entities.Client;
import ma.enset.backend.entities.Credit;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientDTO fromClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        
        if (client.getCredits() != null) {
            clientDTO.setCreditIds(
                client.getCredits().stream()
                    .map(Credit::getId)
                    .collect(Collectors.toList())
            );
        }
        
        return clientDTO;
    }
    
    public Client toClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        return client;
    }
}