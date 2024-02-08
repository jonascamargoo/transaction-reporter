import { Routes } from '@angular/router';
import { OperationsListComponent } from './components/operations-list/operations-list.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';


export const routes: Routes = [
    { path: "", component: FileUploadComponent },
    { path: "upload", component: FileUploadComponent },
    { path: "transacoes", component: OperationsListComponent }
];
