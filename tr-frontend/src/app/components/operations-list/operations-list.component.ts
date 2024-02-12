import { Component, OnInit, inject } from '@angular/core';
import { Operation } from '../../Operation';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FileUploadService } from '../../services/file-upload.service';

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

  constructor(private uploadService: FileUploadService) {}

  ngOnInit(): void {
    this.loadOperations();
  }

  loadOperations() {
    this.uploadService.listOperations().subscribe((operations: any) => {
      console.log(operations);
      this.operations = operations;
    })
  }

}
