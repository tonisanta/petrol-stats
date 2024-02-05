# Petrol stats
The project aims to explore how petrol prices fluctuate based on
geographical zones. Prices for each petrol station are publicly available,
shared by the government through a REST API.

The processing logic for the information is managed within a Java Spring Boot
application. This application communicates with the reports service using
RabbitMQ. Every 30 minutes, based on the Government API, new prices are available
and triggers the publication of a message to inform consumers.

A Python service is in place to generate reports, specifically prices by
province maps. This service actively listens for messages and generates
new reports each time an update occurs.

## Grafana

[Login Grafana](https://8kfsh5qfv5.execute-api.eu-west-1.amazonaws.com/) - Credentials: guest/guest

The most important dashboards are those, showing the "business metrics", however
there are some others showing requests, cpu/memory usage, etc.

![Provinces map](./images/grafana-map.png)

![Prices dashboard](./images/grafana-prices.png)


## Diagrams

The entrypoint is available in public internet as it's exposed using AWS API
Gateway, acting as a reverse proxy to the local network.
![General overview](./images/petrol-overview.svg)

Observability is a must, and this project is not an exception. So I've been play
around with OpenTelemetry and the Collector, to make the apps vendor-agnostic.
However, I would say it's not 100% mature yet, but looks very promising! For
some components, I opted to use Prometheus directly

![Observability](./images/petrol-observability.svg)

Once new data is available, a message is published to a particular exchange.
Reports is actively listening, so when new prices are available, fetches latest 
prices. Providing the new prices in the message body was an option, but I'm open to discuss
the benefits/drawback of each approach.

![Communication](./images/petrol-communication.svg)

Finally, I think it's also interesting to include this one auto-generated,
basically shows the inner layout of the Java application following a
package by feature approach.

![Components](./images/components-application.svg)

