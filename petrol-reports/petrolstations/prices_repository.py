import json
import urllib

from petrolstations.geocategory import GeoCategory


class PricesRepository:
    host: str = "petrol-api:8080"

    def get_prices_by_geocategory(self, geocategory: GeoCategory):
        response = urllib.request.urlopen(f"http://{self.host}/stations/aggregate/{geocategory.name}").read()
        return json.loads(response)
