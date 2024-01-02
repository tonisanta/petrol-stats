import logging

import pika

from reports.reports_generator import ReportsGenerator


class PricesListener:
    host: str = 'rabbitmq'
    exchange: str = 'stations.events'
    routing_key: str = 'prices.updated'

    def __init__(self, reports_generator: ReportsGenerator):
        self.reports_generator = reports_generator

    def listen_events(self):
        connection = pika.BlockingConnection(pika.ConnectionParameters(host=self.host))
        logging.info("connection with pika established")
        channel = connection.channel()

        result = channel.queue_declare(queue='', exclusive=True)
        queue_name = result.method.queue
        logging.info("queue name: %s", queue_name)
        channel.queue_bind(exchange=self.exchange, queue=queue_name, routing_key=self.routing_key)

        logging.info(' [*] Waiting for logs. To exit press CTRL+C')

        def callback(ch, method, properties, body):
            logging.info("msg received, routing key: %s", method.routing_key)
            self.reports_generator.generate_reports()

        channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
        channel.start_consuming()
