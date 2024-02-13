import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { OperationsListComponent } from './components/operations-list/operations-list.component';


export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "simulacoes", component: OperationsListComponent },
    
];
