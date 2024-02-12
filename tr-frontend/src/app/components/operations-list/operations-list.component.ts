import { HttpClient } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { Operation } from '../../Operation';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-operations-list',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule
  ],
  templateUrl: './operations-list.component.html',
})
export class OperationsListComponent implements OnInit {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  // reactiveForms: formControl (um input) formGroup (varios inputs - usar em features futuras)

}
