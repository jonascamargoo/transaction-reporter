import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Operation } from './Operation';

@Component({
  selector: 'app-operations-list',
  standalone: true,
  imports: [],
  templateUrl: './operations-list.component.html',
})
export class OperationsListComponent implements OnInit {

  operations: Operation[];

  constructor() {
    this.operations = [];
  }





  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
