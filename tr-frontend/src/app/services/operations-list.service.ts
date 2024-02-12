import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OperationsListService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080';
  
}
