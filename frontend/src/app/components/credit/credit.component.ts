import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DatePipe, CurrencyPipe } from '@angular/common';

interface Credit {
  id?: number;
  dateDemande: string;
  statut: 'EN_COURS' | 'ACCEPTE' | 'REJETE';
  dateAcceptation?: string;
  montant: number;
  dureeRemboursement: number;
  tauxInteret: number;
  clientId: number;
  type: 'PERSONNEL' | 'IMMOBILIER' | 'PROFESSIONNEL';
  motif?: string;
  typeBien?: 'APPARTEMENT' | 'MAISON' | 'LOCAL_COMMERCIAL';
  raisonSociale?: string;
}

interface Client {
  id: number;
  nom: string;
  email: string;
}

@Component({
  selector: 'app-credit',
  templateUrl: './credit.component.html',
  styleUrl: './credit.component.css',
  standalone: true,
  imports: [FormsModule, DatePipe, CurrencyPipe]
})
export class CreditComponent implements OnInit {
  credits: Credit[] = [];
  clients: Client[] = [];
  credit: Credit = {
    dateDemande: new Date().toISOString().split('T')[0],
    statut: 'EN_COURS',
    montant: 0,
    dureeRemboursement: 0,
    tauxInteret: 0,
    clientId: 0,
    type: 'PERSONNEL'
  };
  isEditing = false;
  private apiUrl = 'http://localhost:8080/api/credits';
  private clientApiUrl = 'http://localhost:8080/api/clients';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadCredits();
    this.loadClients();
  }

  loadCredits() {
    this.http.get<Credit[]>(this.apiUrl).subscribe({
      next: (data) => this.credits = data,
      error: (error) => console.error('Erreur lors du chargement des crédits:', error)
    });
  }

  loadClients() {
    this.http.get<Client[]>(this.clientApiUrl).subscribe({
      next: (data) => this.clients = data,
      error: (error) => console.error('Erreur lors du chargement des clients:', error)
    });
  }

  getClientName(clientId: number): string {
    const client = this.clients.find(c => c.id === clientId);
    return client ? client.nom : 'Client inconnu';
  }

  onSubmit() {
    if (this.isEditing) {
      this.http.put<Credit>(`${this.apiUrl}/${this.credit.id}`, this.credit).subscribe({
        next: () => {
          this.loadCredits();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de la modification:', error)
      });
    } else {
      this.http.post<Credit>(this.apiUrl, this.credit).subscribe({
        next: () => {
          this.loadCredits();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de l\'ajout:', error)
      });
    }
  }

  editCredit(credit: Credit) {
    this.credit = { ...credit };
    this.isEditing = true;
  }

  deleteCredit(id?: number) {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce crédit ?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe({
        next: () => this.loadCredits(),
        error: (error) => console.error('Erreur lors de la suppression:', error)
      });
    }
  }

  resetForm() {
    this.credit = {
      dateDemande: new Date().toISOString().split('T')[0],
      statut: 'EN_COURS',
      montant: 0,
      dureeRemboursement: 0,
      tauxInteret: 0,
      clientId: 0,
      type: 'PERSONNEL'
    };
    this.isEditing = false;
  }

  onTypeChange() {
    this.credit.motif = undefined;
    this.credit.typeBien = undefined;
    this.credit.raisonSociale = undefined;
  }
}
