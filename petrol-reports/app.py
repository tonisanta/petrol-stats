from concurrent.futures import ThreadPoolExecutor
from flask import Flask, jsonify, request
from petrolstations.geocategory import GeoCategory
from petrolstations.prices_repository import PricesRepository
from petrolstations.product import Product
from reports.filter import Filter
from reports.prices_listener import PricesListener
from reports.reports_generator import ReportsGenerator

app = Flask(__name__)

prices_repo = PricesRepository()
reports_generator = ReportsGenerator(prices_repo)
prices_listener = PricesListener(reports_generator)

print("hello")
file = open("spain-provinces.geojson", "r")

executor = ThreadPoolExecutor()
executor.submit(prices_listener.listen_events)



@app.route('/reports/<geocategory>/<product>')
def reports(geocategory: str, product: str):
    # Check if the specified product is a valid enum member
    try:
        geocategory_enum = GeoCategory(geocategory)
        product_enum = Product(product)
        if geocategory_enum == GeoCategory.CITY:
            return jsonify({'error': 'Report by city is not available'}), 400

    except ValueError:
        return jsonify({'error': 'Invalid request'}), 400

    # Get the 'include_outliers' query parameter, using type=bool always evaluates to True, so we
    # have to do this manual comparison
    include_outliers: bool = request.args.get('include_outliers', default=False, type=str).lower() == 'true'

    f = Filter(geocategory_enum, product_enum, include_outliers)
    m = reports_generator.reports_by_filter[f]
    return m.get_root().render()


if __name__ == '__main__':
    app.run()
