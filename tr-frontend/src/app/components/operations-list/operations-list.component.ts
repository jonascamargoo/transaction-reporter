import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { OperationService } from '../../services/operation.service';

@Component({
  selector: 'app-operations-list',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './operations-list.component.html',
})
export class OperationsListComponent implements OnInit {

  http = inject(HttpClient);
  operations: any = [];

  constructor(private operationService: OperationService) {}

  ngOnInit(): void {
    
    // preciso chamar o loadOperations em outro lugar
  }

  loadOperations() {
    this.operationService.listOperations().subscribe((operations: any) => {
      console.log(operations);
      this.operations = operations;
    })
  }

  removeOperations() {
    this.operationService.removeOperations();
  }

}
