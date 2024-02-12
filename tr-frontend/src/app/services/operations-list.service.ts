import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Operation } from '../Operation';

@Injectable({
  providedIn: 'root'
})
export class OperationsListService {

  

  constructor(private http: HttpClient) {

   }

  private baseUrl = 'http://localhost:8080/api';

  // listOperations() {
  //   return this.http.get<Operation>(this.baseUrl);
    
  // }
  
}
