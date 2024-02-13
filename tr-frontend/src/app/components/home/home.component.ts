import { NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import { AboutCNABComponent } from '../about-cnab/about-cnab.component';
import { HeaderComponent } from '../header/header.component';
import { OperationsListComponent } from '../operations-list/operations-list.component';
import { FooterComponent } from '../footer/footer.component';
import { BtnSelectComponent } from '../btn-select/btn-select.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    HeaderComponent,
    OperationsListComponent,
    NgOptimizedImage,
    BtnSelectComponent,
    AboutCNABComponent,
    FooterComponent,
    RouterLink
  ],
  templateUrl: './home.component.html',
})
export class HomeComponent {

}
