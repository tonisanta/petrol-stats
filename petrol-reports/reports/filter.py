from dataclasses import dataclass

from petrolstations.geocategory import GeoCategory
from petrolstations.product import Product


@dataclass(frozen=True)
class Filter:
    geocategory: GeoCategory
    product: Product
    include_outliers: bool


