import { Routes } from '@angular/router';
import { OperationsListComponent } from './components/operations-list/operations-list.component';
import { UploadComponent } from './components/upload/upload.component';


export const routes: Routes = [
    { path: "", component: UploadComponent },
    { path: "upload", component: UploadComponent },
    { path: "transacoes", component: OperationsListComponent }
];
