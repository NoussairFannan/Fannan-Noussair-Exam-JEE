package ma.enset.backend;

import ma.enset.backend.entities.Client;
import ma.enset.backend.entities.Credit;
import ma.enset.backend.entities.PersonalCredit;
import ma.enset.backend.entities.Repayment;
import ma.enset.backend.enums.CreditStatus;
import ma.enset.backend.repositories.ClientRepository;
import ma.enset.backend.repositories.CreditRepository;
import ma.enset.backend.repositories.RepaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ClientRepository clientRepository,
                            CreditRepository creditRepository,
                            RepaymentRepository repaymentRepository) {
        return args -> {
            System.out.println("=== Testing DAO Layer ===");

            // Test ClientRepository
            System.out.println("\n=== Testing ClientRepository ===");

            Client client1 = new Client();
            client1.setName("John Doe");
            client1.setEmail("john@example.com");
            clientRepository.save(client1);

            Client client2 = new Client();
            client2.setName("Jane Smith");
            client2.setEmail("jane@example.com");
            clientRepository.save(client2);

            System.out.println("All clients:");
            List<Client> clients = clientRepository.findAll();
            clients.forEach(c -> System.out.println("- " + c.getName() + " (" + c.getEmail() + ")"));

            Client foundClient = clientRepository.findById(client1.getId()).orElse(null);
            System.out.println("Found client by ID: " + (foundClient != null ? foundClient.getName() : "Not found"));

            // Test CreditRepository
            System.out.println("\n=== Testing CreditRepository ===");

            PersonalCredit credit1 = new PersonalCredit();
            credit1.setClient(client1);
            credit1.setAmount(10000.0);
            credit1.setInterestRate(5.5);
            credit1.setRepaymentDuration(24);
            credit1.setRequestDate(new Date());
            credit1.setStatus(CreditStatus.IN_PROGRESS);
            credit1.setPurpose("Home renovation");
            creditRepository.save(credit1);

            System.out.println("All credits:");
            List<Credit> credits = creditRepository.findAll();
            credits.forEach(c -> System.out.println("- Amount: " + c.getAmount() + ", Client: " + c.getClient().getName()));

            Credit foundCredit = creditRepository.findById(credit1.getId()).orElse(null);
            System.out.println("Found credit by ID: " + (foundCredit != null ? foundCredit.getAmount() : "Not found"));

            // Test RepaymentRepository
            System.out.println("\n=== Testing RepaymentRepository ===");

            Repayment repayment1 = new Repayment();
            repayment1.setCredit(credit1);
            repayment1.setAmount(500.0);
            repayment1.setRepaymentDate(new Date());
            repaymentRepository.save(repayment1);

            Repayment repayment2 = new Repayment();
            repayment2.setCredit(credit1);
            repayment2.setAmount(500.0);
            repayment2.setRepaymentDate(new Date());
            repaymentRepository.save(repayment2);

            System.out.println("All repayments:");
            List<Repayment> repayments = repaymentRepository.findAll();
            repayments.forEach(r -> System.out.println("- Amount: " + r.getAmount() + ", Credit ID: " + r.getCredit().getId()));

            Repayment foundRepayment = repaymentRepository.findById(repayment1.getId()).orElse(null);
            System.out.println("Found repayment by ID: " + (foundRepayment != null ? foundRepayment.getAmount() : "Not found"));

            System.out.println("\n=== Testing Update Operations ===");

            client1.setName("John Doe Updated");
            clientRepository.save(client1);
            System.out.println("Updated client: " + clientRepository.findById(client1.getId()).get().getName());

            credit1.setAmount(12000.0);
            credit1.setStatus(CreditStatus.ACCEPTED);
            credit1.setAcceptanceDate(new Date());
            creditRepository.save(credit1);
            Credit updatedCredit = creditRepository.findById(credit1.getId()).get();
            System.out.println("Updated credit: Amount=" + updatedCredit.getAmount() + ", Status=" + updatedCredit.getStatus());

            repayment1.setAmount(600.0);
            repaymentRepository.save(repayment1);
            System.out.println("Updated repayment: " + repaymentRepository.findById(repayment1.getId()).get().getAmount());

        };
    }
}
