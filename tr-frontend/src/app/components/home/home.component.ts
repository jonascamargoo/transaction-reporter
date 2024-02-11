import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import { AboutCNABComponent } from '../about-cnab/about-cnab.component';
import { FileUploadComponent } from '../file-upload/file-upload.component';
import { HeaderComponent } from '../header/header.component';
import { OperationsListComponent } from '../operations-list/operations-list.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    OperationsListComponent,
    NgOptimizedImage,
    FileUploadComponent,
    AboutCNABComponent,
    FooterComponent
  ],
  templateUrl: './home.component.html',
})
export class HomeComponent {

}
