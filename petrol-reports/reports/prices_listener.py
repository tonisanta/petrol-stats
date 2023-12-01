import pika

from petrolstations.geocategory import GeoCategory
from petrolstations.product import Product
from reports.filter import Filter
from reports.reports_generator import ReportsGenerator


class PricesListener:
    host: str = 'rabbitmq'
    exchange: str = 'stations.events'
    routing_key: str = 'prices.updated'

    def __init__(self, reports_generator: ReportsGenerator):
        self.reports_generator = reports_generator

    def listen_events(self):
        print("start listening events")
        connection = pika.BlockingConnection(pika.ConnectionParameters(host=self.host))
        print("connection with pika")
        channel = connection.channel()
        print("got channel")

        # channel.exchange_declare(exchange=self.exchange, exchange_type='direct')
        result = channel.queue_declare(queue='', exclusive=True)
        queue_name = result.method.queue
        print(f"queue name {queue_name}")
        channel.queue_bind(exchange=self.exchange, queue=queue_name, routing_key=self.routing_key)

        print(' [*] Waiting for logs. To exit press CTRL+C')

        def callback(ch, method, properties, body):
            print(f" [x] {method.routing_key}:{body}")

            for category in GeoCategory:
                if category == GeoCategory.CITY:
                    continue

                for prod in Product:
                    f = Filter(category, prod, False)
                    print(f"listener generating report, f: {f}")
                    self.reports_generator.generate_report(f)
                    f2 = Filter(category, prod, True)
                    self.reports_generator.generate_report(f2)

            print("listener, reports done")

        channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

        channel.start_consuming()
