import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OriginDestinationService {
  private results = new BehaviorSubject(null);
  constructor(private http: HttpClient) { }


  setResults(data: any) {
    this.results.next(data);
  }

  getResults(): any {
    return this.results.asObservable();
  }

  getAirports() {
    return this.http.get<any>(`${environment.apiUrl}/airports`);
  }

  getFare(originCode: string, destinationCode: string) {
    return this.http.get<any>(`${environment.apiUrl}/fare/`+originCode+`/`+destinationCode);
  }
}
