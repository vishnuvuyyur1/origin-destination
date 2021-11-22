import { PriceResultComponent } from './price-result.component';
import { OriginDestinationService } from 'src/app/service';
import { of } from 'rxjs';
describe('PriceResultComponent', () => {
  let component: PriceResultComponent;

  const resultMock ={
    fare:{
      currency: 'EUR'
    }
  }
  const originDestinationService = ({
    getResults: jest.fn(()=> of(resultMock))
  } as unknown) as OriginDestinationService;

  beforeEach(() => {
    component= new PriceResultComponent(originDestinationService);
  });

  it('should create', () => {
    expect(component).toBeInstanceOf(PriceResultComponent);
  });

  it('should assign price result on init', () => {
    component.ngOnInit()
    expect(component.fareDetails.fare.currency).toBe('EUR')
  });
});
