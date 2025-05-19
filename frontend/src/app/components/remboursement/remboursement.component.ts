import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DatePipe, CurrencyPipe } from '@angular/common';

interface Remboursement {
  id?: number;
  dateRemboursement: string;
  montant: number;
  creditId: number;
}

interface Credit {
  id: number;
  montant: number;
  clientId: number;
}

@Component({
  selector: 'app-remboursement',
  templateUrl: './remboursement.component.html',
  styleUrl: './remboursement.component.css',
  standalone: true,
  imports: [FormsModule, DatePipe, CurrencyPipe]
})
export class RemboursementComponent implements OnInit {
  remboursements: Remboursement[] = [];
  credits: Credit[] = [];
  remboursement: Remboursement = {
    dateRemboursement: new Date().toISOString().split('T')[0],
    montant: 0,
    creditId: 0
  };
  isEditing = false;
  private apiUrl = 'http://localhost:8080/api/remboursements';
  private creditApiUrl = 'http://localhost:8080/api/credits';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadRemboursements();
    this.loadCredits();
  }

  loadRemboursements() {
    this.http.get<Remboursement[]>(this.apiUrl).subscribe({
      next: (data) => this.remboursements = data,
      error: (error) => console.error('Erreur lors du chargement des remboursements:', error)
    });
  }

  loadCredits() {
    this.http.get<Credit[]>(this.creditApiUrl).subscribe({
      next: (data) => this.credits = data,
      error: (error) => console.error('Erreur lors du chargement des crédits:', error)
    });
  }

  getCreditInfo(creditId: number): string {
    const credit = this.credits.find(c => c.id === creditId);
    return credit ? `Crédit #${credit.id} - ${credit.montant}€` : 'Crédit inconnu';
  }

  onSubmit() {
    if (this.isEditing) {
      this.http.put<Remboursement>(`${this.apiUrl}/${this.remboursement.id}`, this.remboursement).subscribe({
        next: () => {
          this.loadRemboursements();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de la modification:', error)
      });
    } else {
      this.http.post<Remboursement>(this.apiUrl, this.remboursement).subscribe({
        next: () => {
          this.loadRemboursements();
          this.resetForm();
        },
        error: (error) => console.error('Erreur lors de l\'ajout:', error)
      });
    }
  }

  editRemboursement(remboursement: Remboursement) {
    this.remboursement = { ...remboursement };
    this.isEditing = true;
  }

  deleteRemboursement(id?: number) {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce remboursement ?')) {
      this.http.delete(`${this.apiUrl}/${id}`).subscribe({
        next: () => this.loadRemboursements(),
        error: (error) => console.error('Erreur lors de la suppression:', error)
      });
    }
  }

  resetForm() {
    this.remboursement = {
      dateRemboursement: new Date().toISOString().split('T')[0],
      montant: 0,
      creditId: 0
    };
    this.isEditing = false;
  }
}
