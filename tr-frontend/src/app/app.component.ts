import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { OperationsListComponent } from './components/operations-list/operations-list.component';
import { NgOptimizedImage } from '@angular/common';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { CommonModule } from '@angular/common';
import { AboutCNABComponent } from './components/about-cnab/about-cnab.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    HeaderComponent,
    OperationsListComponent,
    NgOptimizedImage,
    FileUploadComponent,
    AboutCNABComponent
  ],
  templateUrl: './app.component.html',
})


export class AppComponent {
  title = 'tr-frontend';
}
