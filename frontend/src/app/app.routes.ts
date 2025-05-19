import { Routes } from '@angular/router';
import { ClientComponent } from './components/client/client.component';
import { CreditComponent } from './components/credit/credit.component';
import { RemboursementComponent } from './components/remboursement/remboursement.component';

export const routes: Routes = [
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  { path: 'clients', component: ClientComponent },
  { path: 'credits', component: CreditComponent },
  { path: 'remboursements', component: RemboursementComponent }
];
