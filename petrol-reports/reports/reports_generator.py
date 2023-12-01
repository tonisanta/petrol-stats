import geopandas as gpd
import pandas as pd

from petrolstations.geocategory import GeoCategory
from petrolstations.prices_repository import PricesRepository
from reports.filter import Filter


class ReportsGenerator:
    reports_by_filter = {}

    info_by_geocategory = {
        GeoCategory.PROVINCE: {
            "index": "cod_prov",
            "file_name": "spain-provinces.geojson",
            "outliers": {
                "Las Palmas": "35",
                "Santa Cruz de Tenerife": "38",
                "Ceuta": "51",
                "Melilla": "52"
            }
        },
        GeoCategory.AUTONOMOUS_COMMUNITY: {
            "index": "cod_ccaa",
            "file_name": "spain-communities.geojson",
            "outliers": {
                "Canarias": "05",
                "Ceuta": "18",
                "Melilla": "19"
            }
        },
    }

    def __init__(self, prices_repository: PricesRepository):
        self.prices_repository = prices_repository

    def generate_report(self, f: Filter):
        prices = self.prices_repository.get_prices_by_geocategory(f.geocategory)

        # "cod_prov" or "cod_ccaa" depending on geocategory
        column_name: str = self.info_by_geocategory[f.geocategory]["index"]

        print("filter")
        print(f)
        print(f.product.value)
        df = pd.DataFrame.from_dict(data=prices, orient="index")
        df = df.reset_index().rename(columns={"index": column_name})
        df[f.product.value] = df[f.product.value].round(decimals=4)

        spain = gpd.read_file(self.info_by_geocategory[f.geocategory]["file_name"])

        if not f.include_outliers:
            data_to_exclude = self.info_by_geocategory[f.geocategory]["outliers"]
            df = df[~df[column_name].isin(data_to_exclude.values())]

        spain = spain.merge(df, how="inner", on=column_name)
        spain = spain.drop(columns=['created_at', 'updated_at'])

        folium_map = spain.explore(
            column=f.product.value,  # make choropleth (legend) based on "price" column
            tooltip=["name", f.product.value],  # display province and price
            popup=True,  # show all values in popup (on click)
            tiles="CartoDB positron",  # use "CartoDB positron" tiles
            cmap="RdYlGn_r",  # use "Set1" matplotlib colormap
            style_kwds=dict(color="black")  # use black outline
        )

        self.reports_by_filter[f] = folium_map
        print("report saved")
