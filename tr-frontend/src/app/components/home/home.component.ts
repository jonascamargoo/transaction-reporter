import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-home', // nome que referencia esse componente em outro component.html (deve ser importado no respectivo compoent.ts)
  standalone: true, // indica que o componente nao será utilizado em outro modulo
  imports: [
    HeaderComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
