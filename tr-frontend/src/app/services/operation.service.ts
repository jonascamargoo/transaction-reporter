import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  private baseUrl = 'http://localhost:8080/api/operacoes';

  constructor(private http: HttpClient) { }

  listOperations(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  removeOperations(): Observable<any> {
    return this.http.delete(`${this.baseUrl}`)
  }
  
}
