import { Component, OnInit } from '@angular/core';
import { OriginDestinationService } from 'src/app/service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

export interface Airport {
  code: string;
  name: string;
  description: string;
}
@Component({
  selector: 'app-price-search',
  templateUrl: './price-search.component.html',
  styleUrls: ['./price-search.component.css']
})
export class PriceSearchComponent implements OnInit {
  loading:boolean =false;
  serviceError:boolean =false;
  filteredAirports: Observable<any[]> | undefined;
  filteredDestinations: Observable<any[]> | undefined;
  priceForm: FormGroup;
  airports: Airport[] = [];


  constructor(private originDestinationService: OriginDestinationService) {
    this.priceForm = new FormGroup
      ({
        origin: new FormControl('', Validators.required),
        destination: new FormControl('', Validators.required)
      });
  }

  ngOnInit(): void {
    this.loading = true;
    this.originDestinationService.getAirports()
      .subscribe({
        next: (data) => {
          this.airports = data
          this.filteredAirports = this.priceForm.controls['origin'].valueChanges
            .pipe(
              startWith(''),
              map(state => state ? this.filterAirports(state) : this.airports.slice())
            );
            this.filteredDestinations = this.priceForm.controls['destination'].valueChanges
            .pipe(
              startWith(''),
              map(state => state ? this.filterAirports(state) : this.airports.slice())
            );
            this.loading = false;
        },
        error: (e) => {
          this.loading = false;
            this.serviceError = true;
        },
      })
  }

  filterAirports(name: string) {
    return this.airports.filter(airport =>
      airport.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  getPrice() {
    let origin = this.priceForm.controls['origin'].value
    let destination = this.priceForm.controls['destination'].value
    if (this.priceForm.invalid || origin===destination) {
      return;
    }
    this.loading = true;
    this.serviceError = false;
    this.originDestinationService.getFare(this.priceForm.controls['origin'].value,this.priceForm.controls['destination'].value )
    .subscribe({
      next: (data) => {
        this.originDestinationService.setResults(data)
        this.loading =false
      },
      error: (e) => {
        this.loading =false;
        this.serviceError = true;
      }
    })
  }



}
