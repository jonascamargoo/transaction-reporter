import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { OperationsListComponent } from './components/operations-list/operations-list.component';
import { NgOptimizedImage } from '@angular/common';
import { FileUploadComponent } from './components/file-upload/file-upload.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    OperationsListComponent,
    NgOptimizedImage,
    FileUploadComponent
  ],
  templateUrl: './app.component.html',
})


export class AppComponent {
  title = 'tr-frontend';
}
