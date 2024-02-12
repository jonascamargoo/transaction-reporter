import { Component, OnInit, inject } from '@angular/core';
import { Operation } from '../../Operation';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

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

  http = inject(HttpClient);
  operations: any = [];

  ngOnInit(): void {
    this.fetchOperations();
  }

  fetchOperations() {
    this.http.get("http://localhost:8080/api/operacoes")
    .subscribe((operations: any) => {
      console.log(operations);
    });
  }

  // reactiveForms: formControl (um input) formGroup (varios inputs - usar em features futuras)

}
