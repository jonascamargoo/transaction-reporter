import { Routes } from '@angular/router';
import { AboutCNABComponent } from './components/about-cnab/about-cnab.component';
import { HomeComponent } from './components/home/home.component';
import { OperationsListComponent } from './components/operations-list/operations-list.component';


export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "operacoes", component: OperationsListComponent },
    
];
