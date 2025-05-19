import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <a class="navbar-brand" href="#">Gestion des Crédits</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" routerLink="/clients" routerLinkActive="active">Clients</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/credits" routerLinkActive="active">Crédits</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/remboursements" routerLinkActive="active">Remboursements</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <router-outlet></router-outlet>
  `,
  styles: [`
    .navbar { margin-bottom: 20px; }
    .container { max-width: 1200px; }
  `]
})
export class AppComponent {
  title = 'Gestion des Crédits';
}
