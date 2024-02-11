import { Routes } from '@angular/router';
import { AboutCNABComponent } from './components/about-cnab/about-cnab.component';
import { HomeComponent } from './components/home/home.component';


export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "upload", component: HomeComponent },
    { path: "cnab", component: AboutCNABComponent }
];
