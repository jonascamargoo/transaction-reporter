import { Routes } from '@angular/router';
import { OperationsListComponent } from './components/operations-list/operations-list.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { AboutCNABComponent } from './components/about-cnab/about-cnab.component';


export const routes: Routes = [
    { path: "", component: FileUploadComponent },
    { path: "upload", component: FileUploadComponent },
    { path: "cnab", component: AboutCNABComponent }
];
