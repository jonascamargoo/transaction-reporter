import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Operation } from '../Operation';

@Injectable({
  providedIn: 'root'
})
export class OperationsListService {

  private baseUrl = 'http://localhost:8080/api/operacoes';
  
  constructor(private http: HttpClient) {}

  listOperations() {
    return this.http.get<Operation>(this.baseUrl);
  }  

  
}
