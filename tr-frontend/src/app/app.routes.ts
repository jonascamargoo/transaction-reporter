import { Routes } from '@angular/router';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { AboutCNABComponent } from './components/about-cnab/about-cnab.component';


export const routes: Routes = [
    { path: "", component: FileUploadComponent },
    { path: "cnab", component: AboutCNABComponent }
];
