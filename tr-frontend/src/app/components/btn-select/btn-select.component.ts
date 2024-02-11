import { CommonModule } from '@angular/common';
import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from 'stream';

@Component({
  selector: 'app-btn-select',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './btn-select.component.html',
})
export class BtnSelectComponent {
  @Input("btn-text") btnText: string = "";
  @Input() disabled: boolean = false;
  @Input() loading: boolean = false;

  // @Output("submit") onSubmit = new EventEmitter(); 
  

}
