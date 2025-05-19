import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface Client {
  id?: number;
  nom: string;
  email: string;
}

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrl: './client.component.css',
  standalone: true,
  imports: [FormsModule]
})
export class ClientComponent implements OnInit {
  clients: Client[] = [];
  client: Client = { nom: '', email: '' };
  isEditing = false;
  private apiUrl = 'http://localhost:8080/clients';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadClients();
  }

  loadClients() {
    this.http.get<Client[]>(this.apiUrl).subscribe({
      next: (data) => this.clients = data,
      error: (error) => console.error('Erreur lors du chargement des clients:', error)
    });
  }

  onSubmit() {
    if (this.isEditing) {
      this.http.put<Client>(`${this.apiUrl}/${this.client.id}`, this.client).subscribe({
        next: () => {
          this.loadClients();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de la modification:', error)
      });
    } else {
      this.http.post<Client>(this.apiUrl, this.client).subscribe({
        next: () => {
          this.loadClients();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de l\'ajout:', error)
      });
    }
  }

  editClient(client: Client) {
    this.client = { ...client };
    this.isEditing = true;
  }

  deleteClient(id?: number) {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe({
        next: () => this.loadClients(),
        error: (error) => console.error('Erreur lors de la suppression:', error)
      });
    }
  }

  resetForm() {
    this.client = { nom: '', email: '' };
    this.isEditing = false;
  }
}
