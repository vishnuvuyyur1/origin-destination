import { Component, OnInit } from '@angular/core';
import { OriginDestinationService } from 'src/app/service';
@Component({
  selector: 'app-price-result',
  templateUrl: './price-result.component.html',
  styleUrls: ['./price-result.component.css']
})
export class PriceResultComponent implements OnInit {
  fareDetails: any;
  constructor(private originDestinationService: OriginDestinationService) { }

  ngOnInit(): void {
    this.originDestinationService.getResults().subscribe({
      next: (data: any) => this.fareDetails=data
    })
  }


}
